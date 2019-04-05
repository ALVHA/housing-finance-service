package com.riverway.housingfinance.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BankSupportAmountResponse {

    private String bank;
    private List<YearlyAverageAmount> supportAmount;
}
