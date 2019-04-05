package com.riverway.housingfinance.finance.dto;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinance;
import com.riverway.housingfinance.finance.support.CsvFilePreprocessor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SupplyStatusData {

    private List<BankName> bankNames;
    private List<String> rows;

    public SupplyStatusData(List<BankName> bankNames, List<String> rows) {
        this.bankNames = bankNames;
        this.rows = rows;
    }

//    public TotalFinance convertToMonthlyDataOfBank(List<Bank> banks) {
//        TotalFinance totalFinance = new TotalFinance();
//        List<Bank> sortedBanks = sortBank(banks);
//        for (String row : rows) {
//            int[] values = CsvFilePreprocessor.filterEmptyDataToInt(row);
//            for (int i = 2; i < values.length; i++) {
//                int year = values[0];
//                int month = values[1];
//                int amount = values[i];
//                Bank bank = sortedBanks.get(i - 2);
//
//                MonthlyFinance monthlyData = new MonthlyFinance(year, month, amount, bank);
//                totalFinance.addMonthlyFinance(monthlyData);
//            }
//        }
//        return totalFinance;
//    }

    public TotalFinance convertToMonthlyDataOfBank(List<Bank> banks) {
        List<Bank> sortedBanks = sortBank(banks);
        List<MonthlyFinance> monthlyFinances = new ArrayList<>();
        for (String row : rows) {
            int[] values = CsvFilePreprocessor.filterEmptyDataToInt(row);
            for (int i = 2; i < values.length; i++) {
                int year = values[0];
                int month = values[1];
                int amount = values[i];
                Bank bank = sortedBanks.get(i - 2);

                MonthlyFinance monthlyData = new MonthlyFinance(year, month, amount, bank);
                monthlyFinances.add(monthlyData);
            }
        }
        return new TotalFinance(monthlyFinances);
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
