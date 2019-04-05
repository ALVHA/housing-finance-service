package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinance;
import com.riverway.housingfinance.finance.domain.YearlyFinance;
import com.riverway.housingfinance.finance.domain.repository.YearlyFinanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YearlyFinanceService {

    private final YearlyFinanceRepository yearlyFinanceRepository;

    public YearlyFinanceService(YearlyFinanceRepository yearlyFinanceRepository) {
        this.yearlyFinanceRepository = yearlyFinanceRepository;
    }

    public void saveYearlyFinaces(Map<Integer, Map<Bank, List<MonthlyFinance>>> yearlyFinances) {
        for (Integer year : yearlyFinances.keySet()) {
            Map<Bank, List<MonthlyFinance>> amountByBank = yearlyFinances.get(year);
            saveYearlyFinance(year, amountByBank);
        }
    }

    public void saveYearlyFinance(int year, Map<Bank, List<MonthlyFinance>> amountByBank) {
        for (Bank bank : amountByBank.keySet()) {
            Integer amount = amountByBank.get(bank).stream().mapToInt(MonthlyFinance::getAmount).sum();
            YearlyFinance yearlyFinance = new YearlyFinance(year, amount, bank);
            yearlyFinanceRepository.save(yearlyFinance);
        }
    }

    public List<YearlyFinance> findYearlyFinances() {
        return yearlyFinanceRepository.findYearlyFinances();
    }
}
