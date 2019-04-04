package com.riverway.housingfinance.finance.support;

import com.riverway.housingfinance.bank.BankAmountResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class YearlyFinanceResponse {

    private int year;
    private int totalAmount;
    private List<BankAmountResponse> detailAmount;

    public YearlyFinanceResponse(int year, List<BankAmountResponse> detailAmount) {
        this.year = year;
        this.totalAmount = calculateTotalAmount(detailAmount);
        this.detailAmount = detailAmount;
    }

    public int calculateTotalAmount(List<BankAmountResponse> detailAmount) {
        return detailAmount
                .stream()
                .mapToInt(data -> data.getAmount())
                .sum();
    }
}
