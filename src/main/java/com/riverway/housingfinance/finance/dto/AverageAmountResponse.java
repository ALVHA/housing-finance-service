package com.riverway.housingfinance.finance.dto;

import com.riverway.housingfinance.finance.domain.YearlyFinance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AverageAmountResponse {

    private String bank;
    private List<AverageAmount> supportAmount;
}
