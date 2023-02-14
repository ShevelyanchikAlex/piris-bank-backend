package com.bsuir.piris.service;

import com.bsuir.piris.model.dto.AccountDto;
import com.bsuir.piris.model.dto.DepositDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepositService {
    DepositDto save(DepositDto dto);

    DepositDto findById(Long id);

    Page<DepositDto> findAll(Pageable pageable);

    Page<AccountDto> findAllAccounts(Pageable pageable);

    void deleteById(Long id);

    void closeDeposit(Long id);

    void closeDay(Integer monthAmount);
}
