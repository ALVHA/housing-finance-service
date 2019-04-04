package com.riverway.housingfinance.domain;

import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
public class YearlyFinance {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    public YearlyFinance(int year, int amount, Bank bank) {
        this.year = year;
        this.amount = amount;
        this.bank = bank;
    }
}
