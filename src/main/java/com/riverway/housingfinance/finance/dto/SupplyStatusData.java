package com.riverway.housingfinance.finance.dto;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
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

    public List<MonthlyFinanceSupply> parse(List<Bank> banks) {
        List<Bank> sortedBanks = sortBank(banks);
        List<MonthlyFinanceSupply> monthlyFinanceSupplies = new ArrayList<>();
        for (String row : rows) {
            int[] values = CsvFilePreprocessor.filterEmptyDataToInt(row);
            for (int i = 2; i < values.length; i++) {
                int year = values[0];
                int month = values[1];
                int amount = values[i];
                Bank bank = sortedBanks.get(i - 2);

                MonthlyFinanceSupply monthlyData = new MonthlyFinanceSupply(year, month, amount, bank);
                monthlyFinanceSupplies.add(monthlyData);
            }
        }
        return monthlyFinanceSupplies;
    }

    private List<Bank> sortBank(List<Bank> banks) {
        List<Bank> sortedBanks = new ArrayList<>();
        for (BankName name : bankNames) {
            sortedBanks.add(name.match(banks));
        }
        return sortedBanks;
    }
}
