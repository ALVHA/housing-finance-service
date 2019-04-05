package com.riverway.housingfinance.finance.dto;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class SupplyStatusData {

    private List<BankName> bankNames;
    private List<String> rows;

    public SupplyStatusData(String title, List<String> rows) {
        this.bankNames = parseTitle(title);
        this.rows = rows;
    }

    public List<MonthlyFinanceSupply> parse(List<Bank> banks) {
        List<Bank> sortedBanks = sortBank(banks);
        List<MonthlyFinanceSupply> monthlyFinanceSupplies = new ArrayList<>();
        for (String row : rows) {
            int[] values = filterEmptyDataToInt(row);
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

    public List<BankName> parseTitle(String title) {
        String[] titles = cleanseData(title);
        List<BankName> bankNames = new ArrayList<>();
        for (int i = 2; i < titles.length; i++) {
            bankNames.add(BankName.of(titles[i]));
        }
        return bankNames;
    }

    public int[] filterEmptyDataToInt(String row) {
        return Arrays.stream(row.split(","))
                .filter(data -> data.length() > 0)
                .mapToInt(string -> Integer.parseInt(string))
                .toArray();
    }

    public String[] cleanseData(String row) {
        String[] parsedData = row.split(",");
        return filterEmptyData(parsedData);
    }

    public String[] filterEmptyData(String[] row) {
        return Arrays.stream(row)
                .filter(data -> data.length() > 0)
                .toArray(String[]::new);
    }
}
