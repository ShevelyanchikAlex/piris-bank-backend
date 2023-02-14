package com.bsuir.piris.model.dto;

import com.bsuir.piris.model.domain.Currency;
import com.bsuir.piris.model.domain.DepositType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDto {
    private Long id;
    private DepositType depositType;
    private String contractNumber;
    private Currency currency;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer contractTerm;
    private BigDecimal percent;
    private BigDecimal sumAmount;
    private Boolean isOpen;
    private UserDto user;
    private AccountDto currentAccount;
    private AccountDto percentAccount;
}
