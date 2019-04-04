package com.riverway.housingfinance.finance.domain;

import com.riverway.housingfinance.bank.domain.Bank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;

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

    public void includeInTotal(Map<Integer, Map<Bank, Integer>> yearlyFinances) {
        Map<Bank, Integer> amountByBank = yearlyFinances.get(year);
        if (amountByBank == null) {
            amountByBank = new HashMap<>();
            yearlyFinances.put(year, amountByBank);
        }
        includeInTotalOfBank(amountByBank);
    }

    private void includeInTotalOfBank(Map<Bank, Integer> amountByBank) {
        Integer amountOfBank = amountByBank.get(bank);
        if (amountOfBank == null) {
            amountOfBank = new Integer(0);
        }
        amountByBank.put(bank, amountOfBank + amount);
    }
}
