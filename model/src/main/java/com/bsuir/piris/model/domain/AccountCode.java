package com.bsuir.piris.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountCode {
    BANK_CASH("1010"),
    BANK_FUND("7327"),
    PERSONAL("3014");

    private final String code;
}