package com.bsuir.piris.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "deposit")
@NoArgsConstructor
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "deposit_type")
    private DepositType depositType;

    @Column(name = "contract_number")
    private String contractNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "contract_term")
    private Integer contractTerm;

    @Column(name = "percent")
    private BigDecimal percent;

    @Column(name = "sum_amount")
    private BigDecimal sumAmount;

    @Column(name = "is_open")
    private Boolean isOpen = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "current_account_id", referencedColumnName = "id")
    private Account currentAccount;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "percent_account_id", referencedColumnName = "id")
    private Account percentAccount;
}
