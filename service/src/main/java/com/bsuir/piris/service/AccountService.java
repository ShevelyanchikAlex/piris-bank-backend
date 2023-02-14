package com.bsuir.piris.service;

import com.bsuir.piris.model.domain.Account;
import com.bsuir.piris.model.domain.AccountCode;
import com.bsuir.piris.model.dto.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {
    AccountDto findById(Long id);

    Page<AccountDto> findAll(Pageable pageable);

    AccountDto findByAccountCode(AccountCode accountCode);
}
