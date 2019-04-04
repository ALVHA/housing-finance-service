package com.riverway.housingfinance.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class TotalFinance {

    private Map<Integer, Map<Bank, Integer>> yearlyFinances;
    private List<MonthlyFinance> monthlyFinances;

    public TotalFinance() {
        yearlyFinances = new HashMap<>();
        monthlyFinances = new ArrayList<>();
    }

    public void addMonthlyFinance(MonthlyFinance monthlyFinance) {
        monthlyFinances.add(monthlyFinance);
        monthlyFinance.includeInTotal(yearlyFinances);
    }
}
