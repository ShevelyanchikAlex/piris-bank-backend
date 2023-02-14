package com.bsuir.piris.web.controller;

import com.bsuir.piris.model.domain.AccountCode;
import com.bsuir.piris.model.dto.AccountDto;
import com.bsuir.piris.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public List<AccountDto> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<AccountDto> accountDtoPage = accountService.findAll(PageRequest.of(page, size));
        return new ArrayList<>(accountDtoPage.getContent());
    }

    @GetMapping("/{id}")
    public AccountDto findById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @GetMapping("/bank-fund")
    public AccountDto findBankFundAccount() {
        return accountService.findByAccountCode(AccountCode.BANK_FUND);
    }
}
