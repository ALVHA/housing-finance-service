package com.riverway.housingfinance.finance.dto;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinance;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class TotalFinance {

    private List<MonthlyFinance> monthlyFinances;

    public TotalFinance(List<MonthlyFinance> monthlyFinances) {
        this.monthlyFinances = monthlyFinances;
    }

    public Map<Integer, Map<Bank, List<MonthlyFinance>>> groupByYearsForTotal() {
        return monthlyFinances.stream()
                .collect(Collectors.groupingBy(MonthlyFinance::getYear,
                        Collectors.groupingBy(MonthlyFinance::getBank)));
    }
}
