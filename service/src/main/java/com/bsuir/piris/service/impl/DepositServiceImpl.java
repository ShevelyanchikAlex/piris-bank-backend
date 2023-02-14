package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.*;
import com.bsuir.piris.model.dto.DepositDto;
import com.bsuir.piris.model.mapper.DepositMapper;
import com.bsuir.piris.model.mapper.UserMapper;
import com.bsuir.piris.persistence.AccountRepository;
import com.bsuir.piris.persistence.DepositRepository;
import com.bsuir.piris.persistence.ImaginaryTimeRepository;
import com.bsuir.piris.service.DepositService;
import com.bsuir.piris.service.ImaginaryTimeService;
import com.bsuir.piris.service.exception.ServiceException;
import com.bsuir.piris.service.validator.impl.IdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {
    private static final String DEPOSIT_NOT_FOUND_ERROR = "deposit.not.found";
    private static final String IRREVOCABLE_DEPOSIT_CLOSED_ERROR = "irrevocable.deposit.cannot.closed";
    private static final String DEPOSIT_ALREADY_CLOSED_ERROR = "deposit.already.closed";
    private static final String ACCOUNT_NOT_ERROR = "account.not.found";
    private static final BigDecimal PERCENT_SCALE_VALUE = BigDecimal.valueOf(100);
    private static final BigDecimal MONTH_AMOUNT = BigDecimal.valueOf(12);
    private static final int END_VALUE = 9;
    private static final int LIMIT_CONTRACT_NUMBER = 12;
    private static final int FIRST_DAY = 1;

    private final DepositRepository depositRepository;
    private final AccountRepository accountRepository;
    private final ImaginaryTimeRepository imaginaryTimeRepository;
    private final ImaginaryTimeService imaginaryTimeService;
    private final DepositMapper depositMapper;
    private final UserMapper userMapper;
    private final IdValidator idValidator;

    @Transactional
    @Override
    public DepositDto save(DepositDto depositDto) {
        LocalDate startDate = imaginaryTimeService.findLastDate().getCurrentDate();
        LocalDate endDate = startDate.plusMonths(depositDto.getContractTerm());
        depositDto.setStartDate(LocalDate.of(startDate.getYear(), startDate.getMonthValue(), FIRST_DAY));
        depositDto.setEndDate(LocalDate.of(endDate.getYear(), endDate.getMonthValue(), FIRST_DAY));
        depositDto.setContractNumber(createRandomNumber(LIMIT_CONTRACT_NUMBER));

        Account bankCashAccount = findBankCashAccount();
        bankCashAccount.setDebit(bankCashAccount.getDebit().add(depositDto.getSumAmount()));
        bankCashAccount.setCredit(bankCashAccount.getCredit().subtract(depositDto.getSumAmount()));

        Account bankFundAccount = findBankFundAccount();
        bankFundAccount.setCredit(bankFundAccount.getCredit().add(depositDto.getSumAmount()));

        User user = userMapper.toEntity(depositDto.getUser());
        Account currentAccount = createAccount(depositDto.getSumAmount(), user);
        Account percentAccount = createAccount(BigDecimal.ZERO, user);
        Account savedCurrentAccount = accountRepository.save(currentAccount);
        Account savedPercentAccount = accountRepository.save(percentAccount);
        accountRepository.saveAll(List.of(bankCashAccount, bankFundAccount));

        Deposit deposit = depositMapper.toEntity(depositDto);
        deposit.setPercentAccount(savedPercentAccount);
        deposit.setCurrentAccount(savedCurrentAccount);
        deposit.setUser(user);
        return depositMapper.toDto(depositRepository.save(deposit));
    }

    @Override
    public DepositDto findById(Long id) {
        idValidator.validate(id);
        return depositRepository.findById(id)
                .map(depositMapper::toDto)
                .orElseThrow(() -> new ServiceException(DEPOSIT_NOT_FOUND_ERROR));
    }

    @Override
    public Page<DepositDto> findAll(Pageable pageable) {
        List<DepositDto> depositDtoList = depositRepository.findAll(pageable)
                .stream()
                .map(depositMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(depositDtoList, pageable, depositRepository.count());
    }

    @Override
    public Long getDepositsCount() {
        return depositRepository.count();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        idValidator.validate(id);
        if (!depositRepository.existsById(id)) {
            throw new ServiceException(DEPOSIT_NOT_FOUND_ERROR);
        }
        depositRepository.deleteById(id);
    }

    @Override
    public void closeDeposit(Long id) {
        Deposit deposit = depositRepository.findById(id)
                .orElseThrow(() -> new ServiceException(DEPOSIT_NOT_FOUND_ERROR));

        ImaginaryTime date = imaginaryTimeService.findLastDate();
        if (deposit.getDepositType().equals(DepositType.IRREVOCABLE)
                && deposit.getEndDate().isAfter(date.getCurrentDate())) {
            throw new ServiceException(IRREVOCABLE_DEPOSIT_CLOSED_ERROR);
        } else if (deposit.getIsOpen().equals(Boolean.FALSE)) {
            throw new ServiceException(DEPOSIT_ALREADY_CLOSED_ERROR);
        }
        BigDecimal depositSum = deposit.getSumAmount();
        BigDecimal percentSum = deposit.getPercent();

        Account bankFundAccount = findBankFundAccount();
        bankFundAccount.setDebit(bankFundAccount.getDebit().subtract(depositSum).subtract(percentSum));

        Account bankCashAccount = findBankCashAccount();
        bankCashAccount.setDebit(bankCashAccount.getDebit().add(depositSum));
        bankCashAccount.setCredit(bankCashAccount.getCredit().subtract(depositSum));

        Account percentAccount = deposit.getPercentAccount();
        percentAccount.setCredit(percentAccount.getCredit().add(percentSum));
        percentAccount.setDebit(percentAccount.getDebit().subtract(percentSum));

        Account currentAccount = deposit.getCurrentAccount();
        currentAccount.setCredit(currentAccount.getCredit().add(percentSum));
        currentAccount.setDebit(currentAccount.getDebit().subtract(percentSum));

        accountRepository.saveAll(List.of(bankFundAccount, bankCashAccount, currentAccount, percentAccount));
        deposit.setIsOpen(false);
        depositRepository.save(deposit);
    }

    @Override
    public void closeDay(Integer monthAmount) {
        ImaginaryTime date = imaginaryTimeService.findLastDate();
        LocalDate previousDate = date.getCurrentDate();
        LocalDate currentDate = date.getCurrentDate().plusMonths(monthAmount);
        date.setCurrentDate(currentDate);
        imaginaryTimeRepository.save(date);

        Account bankFundAccount = findBankFundAccount();
        Account bankCashAccount = findBankCashAccount();

        List<Deposit> deposits = depositRepository.findAllByIsOpenIsTrueOrderByStartDateDesc(Pageable.unpaged()).get()
                .map(deposit -> calculatePercents(deposit, previousDate, currentDate, bankFundAccount, bankCashAccount))
                .collect(Collectors.toList());
        List<Account> accounts = deposits
                .stream()
                .map(deposit -> List.of(deposit.getCurrentAccount(), deposit.getPercentAccount()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        accounts.addAll(List.of(bankCashAccount, bankFundAccount));
        accountRepository.saveAll(accounts);
        depositRepository.saveAll(deposits);
    }


    private Deposit calculatePercents(Deposit deposit, LocalDate previousDate, LocalDate currentDate,
                                      Account bankFundAccount, Account bankCashAccount) {
        BigDecimal monthPercents = deposit.getPercent()
                .multiply(deposit.getSumAmount())
                .divide(MONTH_AMOUNT, RoundingMode.HALF_EVEN)
                .divide(PERCENT_SCALE_VALUE, RoundingMode.DOWN);

        LocalDate startDate = previousDate.isAfter(deposit.getStartDate())
                ? previousDate
                : deposit.getStartDate();
        LocalDate endDate = currentDate.isAfter(deposit.getEndDate())
                ? deposit.getEndDate()
                : currentDate;
        Period period = Period.between(startDate, endDate);
        int months = (int) period.toTotalMonths();

        BigDecimal percentMoney = monthPercents.multiply(BigDecimal.valueOf(months));

        bankFundAccount.setDebit(bankFundAccount.getDebit().subtract(percentMoney));

        Account percentAccount = deposit.getPercentAccount();
        percentAccount.setCredit(percentAccount.getCredit().add(percentMoney));
        percentAccount.setDebit(percentAccount.getDebit().subtract(percentMoney));

        bankCashAccount.setDebit(bankCashAccount.getDebit().add(percentMoney));
        bankCashAccount.setCredit(bankCashAccount.getCredit().subtract(percentMoney));
        accountRepository.saveAll(List.of(percentAccount, bankCashAccount));

        if (deposit.getEndDate().isBefore(currentDate) || deposit.getEndDate().isEqual(currentDate)) {
            closeDeposit(deposit, bankCashAccount, bankFundAccount);
        }
        return deposit;
    }

    private void closeDeposit(Deposit deposit, Account bankCashAccount, Account bankFundAccount) {
        BigDecimal depositSum = deposit.getSumAmount();
        bankFundAccount.setDebit(bankFundAccount.getDebit().subtract(depositSum));

        Account percentAccount = deposit.getPercentAccount();
        Account currentAccount = deposit.getCurrentAccount();
        currentAccount.setCredit(currentAccount.getCredit().add(percentAccount.getCredit()));
        currentAccount.setDebit(currentAccount.getDebit().subtract(percentAccount.getCredit()));

        bankCashAccount.setDebit(bankCashAccount.getDebit().add(depositSum));
        bankCashAccount.setCredit(bankCashAccount.getCredit().subtract(depositSum));
        deposit.setIsOpen(false);
    }

    private Account createAccount(BigDecimal amount, User user) {
        Account account = new Account();
        account.setDebit(BigDecimal.ZERO.subtract(amount));
        account.setCredit(amount);
        account.setAccountActivity(AccountActivity.PASSIVE);
        account.setAccountCode(AccountCode.PERSONAL);
        account.setNumber(AccountCode.PERSONAL.getCode().concat(createRandomNumber(END_VALUE)));
        account.setClientData(user.getName().concat(" ").concat(user.getFathersName())
                .concat(" ").concat(user.getSurname()));
        return account;
    }

    private String createRandomNumber(int limit) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < limit; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    private Account findBankFundAccount() {
        return accountRepository.findByAccountCode(AccountCode.BANK_FUND)
                .orElseThrow(() -> new ServiceException(ACCOUNT_NOT_ERROR));
    }

    private Account findBankCashAccount() {
        return accountRepository.findByAccountCode(AccountCode.BANK_CASH)
                .orElseThrow(() -> new ServiceException(ACCOUNT_NOT_ERROR));
    }
}