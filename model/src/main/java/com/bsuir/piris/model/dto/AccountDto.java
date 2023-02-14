package com.bsuir.piris.model.dto;

import com.bsuir.piris.model.domain.AccountActivity;
import com.bsuir.piris.model.domain.AccountCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String number;
    private AccountCode accountCode;
    private AccountActivity accountActivity;
    private BigDecimal debit;
    private BigDecimal credit;
    private String clientData;
}
