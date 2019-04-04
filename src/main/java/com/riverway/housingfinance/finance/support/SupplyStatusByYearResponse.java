package com.riverway.housingfinance.finance.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupplyStatusByYearResponse {

    private String name;
    private List<YearlyFinanceResponse> supplyStatus;
}
