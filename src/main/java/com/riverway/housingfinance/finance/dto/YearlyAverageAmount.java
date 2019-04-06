package com.riverway.housingfinance.finance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class YearlyAverageAmount {

    private static final int TOTAL_MONTH = 12;

    private int year;
    private int amount;

    public YearlyAverageAmount(int year, int amount) {
        this.year = year;
        this.amount = calculateAverage(amount);
    }

    public int calculateAverage(int amount) {
        return Math.round((float) amount / TOTAL_MONTH);
    }
}
