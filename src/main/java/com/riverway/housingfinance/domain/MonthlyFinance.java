package com.riverway.housingfinance.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyFinance {

    @Id
    @GeneratedValue
    private Long id;
    //LocalDateTime 과 int중
    @Column(nullable = false)
    private int year;

    @Min(1)@Max(12)
    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    public MonthlyFinance(int year, int month, int amount, Bank bank) {
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.bank = bank;
    }
}
