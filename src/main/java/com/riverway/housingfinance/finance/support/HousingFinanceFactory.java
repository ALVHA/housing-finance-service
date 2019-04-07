package com.riverway.housingfinance.finance.support;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import com.riverway.housingfinance.finance.dto.MonthlyFinanceDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class HousingFinanceFactory {

    private List<BankName> bankNames;
    private List<String> rows;

    public HousingFinanceFactory(List<BankName> bankNames, List<String> rows) {
        this.bankNames = bankNames;
        this.rows = rows;
    }

    public List<YearlyFinanceSupply> parse(List<Bank> banks) {
        List<Bank> sortedBanks = sortBank(banks);
        List<MonthlyFinanceDto> monthlyFinances = new ArrayList<>();
        for (String row : rows) {
            int[] values = CsvFilePreprocessor.filterEmptyDataToInt(row);
            for (int i = 2; i < values.length; i++) {
                int year = values[0];
                int month = values[1];
                int amount = values[i];
                Bank bank = sortedBanks.get(i - 2);

                MonthlyFinanceDto monthlyData = new MonthlyFinanceDto(year, month, amount, bank);
                monthlyFinances.add(monthlyData);
            }
        }
        return batch(monthlyFinances);
    }

    private List<Bank> sortBank(List<Bank> banks) {
        List<Bank> sortedBanks = new ArrayList<>();
        for (BankName name : bankNames) {
            sortedBanks.add(name.match(banks));
        }
        return sortedBanks;
    }

    private List<YearlyFinanceSupply> batch(List<MonthlyFinanceDto> monthlyFinances) {
        Map<Integer, Map<Bank, List<MonthlyFinanceDto>>> total = groupMonthlyDataByYear(monthlyFinances);
        List<YearlyFinanceSupply> yearlyFinances = new ArrayList<>();
        for (Integer year : total.keySet()) {
            Map<Bank, List<MonthlyFinanceDto>> amountByBank = total.get(year);
            yearlyFinances.addAll(createYearlyData(year, amountByBank));
        }
        return yearlyFinances;
    }

    private Map<Integer, Map<Bank, List<MonthlyFinanceDto>>> groupMonthlyDataByYear(List<MonthlyFinanceDto> monthlyFinances) {
        return monthlyFinances.stream()
                .collect(Collectors.groupingBy(MonthlyFinanceDto::getYear,
                        Collectors.groupingBy(MonthlyFinanceDto::getBank)));
    }

    private List<YearlyFinanceSupply> createYearlyData(int year, Map<Bank, List<MonthlyFinanceDto>> amountByBank) {
        List<YearlyFinanceSupply> yearlyDataOfBank = new ArrayList<>();
        for (Bank bank : amountByBank.keySet()) {
            List<MonthlyFinanceSupply> monthlyData = mapToEntity(amountByBank.get(bank));
            yearlyDataOfBank.add(new YearlyFinanceSupply(year, bank, monthlyData));
        }
        return yearlyDataOfBank;
    }

    private List<MonthlyFinanceSupply> mapToEntity(List<MonthlyFinanceDto> dtos) {
        return dtos.stream().map(MonthlyFinanceDto::toEntity).collect(Collectors.toList());
    }
}
