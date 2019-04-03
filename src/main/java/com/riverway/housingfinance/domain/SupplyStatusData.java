package com.riverway.housingfinance.domain;

import com.riverway.housingfinance.support.HousingFinancePreprocessor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SupplyStatusData {

    private List<BankName> bankNames;
    private List<String> rows;

    public List<MonthlyFinance> convertToMonthlyDataOfBank(List<Bank> banks) {
        List<Bank> sortedBanks = sortBank(banks);
        List<MonthlyFinance> monthlyFinances = new ArrayList<>();
        rows
                .stream()
                .forEach(row -> {
                    int[] values = HousingFinancePreprocessor.filterEmptyDataToInt(row);
                    for (int i = 2; i < values.length; i++) {
                        int year = values[0];
                        int month = values[1];
                        int amount = values[i];
                        monthlyFinances.add(new MonthlyFinance(year, month, amount, sortedBanks.get(i - 2)));
                    }
                });
        return monthlyFinances;
    }

    private List<Bank> sortBank(List<Bank> banks) {
        List<Bank> sortedBanks = new ArrayList<>();
        for (BankName name : bankNames) {
            sortedBanks.add(find(name, banks));
        }
        return sortedBanks;
    }

    private Bank find(BankName name, List<Bank> banks) {
        for (Bank bank : banks) {
            if (bank.match(name)) {
                return bank;
            }
        }
        return null;
    }
}
