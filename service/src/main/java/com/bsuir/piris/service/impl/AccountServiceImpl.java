package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.AccountCode;
import com.bsuir.piris.model.dto.AccountDto;
import com.bsuir.piris.model.mapper.AccountMapper;
import com.bsuir.piris.persistence.AccountRepository;
import com.bsuir.piris.service.AccountService;
import com.bsuir.piris.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final String ACCOUNT_NOT_FOUND_ERROR = "account.not.found";

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDto findById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new ServiceException(ACCOUNT_NOT_FOUND_ERROR));
    }

    @Override
    public Page<AccountDto> findAll(Pageable pageable) {
        List<AccountDto> accountDtoList = accountRepository.findAll(pageable)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(accountDtoList, pageable, accountRepository.count());
    }

    @Override
    public AccountDto findByAccountCode(AccountCode accountCode) {
        return accountRepository.findByAccountCode(accountCode)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new ServiceException(ACCOUNT_NOT_FOUND_ERROR));
    }
}
