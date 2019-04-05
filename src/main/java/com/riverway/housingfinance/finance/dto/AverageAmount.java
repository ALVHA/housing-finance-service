package com.riverway.housingfinance.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AverageAmount {

    private static final int TOTAL_MONTH = 12;

    private int year;
    private int amount;

    public AverageAmount(int year, int amount) {
        this.year = year;
        this.amount = calculateAverage(amount);
    }

    public int calculateAverage(int amount) {
        return Math.round((float) amount / TOTAL_MONTH);
    }
}
