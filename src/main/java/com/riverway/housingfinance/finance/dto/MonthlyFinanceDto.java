package com.riverway.housingfinance.finance.dto;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonthlyFinanceDto {

    private int year;
    private int month;
    private int amount;
    private Bank bank;

    public int getYear() {
        return year;
    }

    public Bank getBank() {
        return bank;
    }

    public MonthlyFinanceSupply toEntity() {
        return new MonthlyFinanceSupply(month, amount);
    }
}
