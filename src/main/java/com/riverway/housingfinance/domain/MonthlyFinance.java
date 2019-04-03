package com.riverway.housingfinance.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyFinance {

    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;

    @Id
    @GeneratedValue
    private Long id;
    //LocalDateTime 과 int중
    private int year;
    private int month;
    private int amount;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    public MonthlyFinance(int year, int month, int amount, Bank bank) {

    }
}
