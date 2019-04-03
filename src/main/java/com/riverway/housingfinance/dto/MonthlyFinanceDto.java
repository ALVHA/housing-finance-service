package com.riverway.housingfinance.dto;

import com.riverway.housingfinance.domain.BankName;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MonthlyFinanceDto {

    private BankName bankName;
    private int year;
    private int month;
    private int amount;
}
