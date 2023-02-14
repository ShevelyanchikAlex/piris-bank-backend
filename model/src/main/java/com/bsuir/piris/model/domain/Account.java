package com.bsuir.piris.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_code")
    private AccountCode accountCode;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_activity")
    private AccountActivity accountActivity;

    @Column(name = "debit")
    private BigDecimal debit;

    @Column(name = "credit")
    private BigDecimal credit;

    @Column(name = "client_data")
    private String clientData;
}
