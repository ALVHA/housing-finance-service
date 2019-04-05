package com.riverway.housingfinance.finance.domain;

import com.riverway.housingfinance.bank.BankAmountResponse;
import com.riverway.housingfinance.finance.dto.SupplyStatusByYearResponse;
import com.riverway.housingfinance.finance.dto.YearlyFinanceBanksResponse;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HousingFinanceSupplyStatus {

    private List<YearlyFinance> supplyStatus;

    public SupplyStatusByYearResponse toResponse() {
        Map<Integer, List<YearlyFinance>> groupByYears = groupByYear(supplyStatus);
        List<YearlyFinanceBanksResponse> yearlyFinanceBanksRespons = new ArrayList<>();
        for (Integer year : groupByYears.keySet()) {
            List<BankAmountResponse> bankAmountResponses = mapToBankAmount(groupByYears.get(year));
            YearlyFinanceBanksResponse yearyFinanceResponse = new YearlyFinanceBanksResponse(year, bankAmountResponses);
            yearlyFinanceBanksRespons.add(yearyFinanceResponse);
        }
        return SupplyStatusByYearResponse.of(yearlyFinanceBanksRespons);
    }

    public Map<Integer, List<YearlyFinance>> groupByYear(List<YearlyFinance> yearlyFinances) {
        return yearlyFinances
                .stream()
                .collect(Collectors.groupingBy(YearlyFinance::getYear));
    }

    public List<BankAmountResponse> mapToBankAmount(List<YearlyFinance> yearlyFinances) {
        return yearlyFinances.stream()
                .map(YearlyFinance::toBankAmount)
                .collect(Collectors.toList());
    }
}
