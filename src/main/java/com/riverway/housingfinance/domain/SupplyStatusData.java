package com.riverway.housingfinance.domain;

import com.riverway.housingfinance.support.HousingFinancePreprocessor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SupplyStatusData {

    private List<BankName> bankNames;
    private List<String> rows;
    private int[][] data;

    public SupplyStatusData(List<BankName> bankNames, List<String> rows) {
        this.bankNames = bankNames;
        this.rows = rows;
    }

    public void convertTo() {
    }

    //    public List<MonthlyFinance> convertToMonthlyDataOfBank(List<Bank> banks) {
//        List<Bank> sortedBanks = sortBank(banks);
//        List<MonthlyFinance> monthlyFinances = new ArrayList<>();
//        rows
//                .stream()
//                .forEach(row -> {
//                    int[] values = HousingFinancePreprocessor.filterEmptyDataToInt(row);
//                    for (int i = 2; i < values.length; i++) {
//                        int year = values[0];
//                        int month = values[1];
//                        int amount = values[i];
//                        monthlyFinances.add(new MonthlyFinance(year, month, amount, sortedBanks.get(i - 2)));
//                    }
//                });
//        return monthlyFinances;
//    }

    public TotalFinance convertToMonthlyDataOfBank(List<Bank> banks) {
        TotalFinance totalFinance = new TotalFinance();
        List<Bank> sortedBanks = sortBank(banks);
        for (String row : rows) {
            int[] values = HousingFinancePreprocessor.filterEmptyDataToInt(row);
            for (int i = 2; i < values.length; i++) {
                int year = values[0];
                int month = values[1];
                int amount = values[i];
                Bank bank = sortedBanks.get(i - 2);

                MonthlyFinance monthlyData = new MonthlyFinance(year, month, amount, bank);
                totalFinance.addMonthlyFinance(monthlyData);
            }
        }
        return totalFinance;
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
