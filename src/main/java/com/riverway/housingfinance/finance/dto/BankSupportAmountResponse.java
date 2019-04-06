package com.riverway.housingfinance.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankSupportAmountResponse {

    private String bank;
    private List<YearlyAverageAmount> supportAmount;
}
