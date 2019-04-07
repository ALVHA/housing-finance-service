package com.riverway.housingfinance.finance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class YearlyFinanceEachBank {

    private int year;
    private int totalAmount;
    private List<BankAmount> detailAmount;

    public YearlyFinanceEachBank(int year, List<BankAmount> detailAmount) {
        this.year = year;
        this.totalAmount = calculateTotalAmount(detailAmount);
        this.detailAmount = detailAmount;
    }

    public int calculateTotalAmount(List<BankAmount> detailAmount) {
        return detailAmount
                .stream()
                .mapToInt(data -> data.getAmount())
                .sum();
    }
}
