package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinance;
import com.riverway.housingfinance.finance.domain.YearlyFinance;
import com.riverway.housingfinance.finance.domain.repository.YearlyFinanceRepository;
import com.riverway.housingfinance.finance.dto.AverageAmount;
import com.riverway.housingfinance.finance.dto.AverageAmountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class YearlyFinanceService {

    private final YearlyFinanceRepository yearlyFinanceRepository;

    public YearlyFinanceService(YearlyFinanceRepository yearlyFinanceRepository) {
        this.yearlyFinanceRepository = yearlyFinanceRepository;
    }

    //너무 복잡함 리팩토링 필요
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

    public YearlyFinance findLargestAmount() {
        return yearlyFinanceRepository
                .findLargestAmount()
                .orElseThrow(EntityExistsException::new);
    }

    public AverageAmountResponse findLargestAndSmallest() {
        YearlyFinance largestValue = yearlyFinanceRepository.findMaxAmountOfExchange();
        YearlyFinance smallestValue = yearlyFinanceRepository.findMinAmountOfExchange();
        log.debug("가장 큰값 : {}, 작은값 : {}", largestValue, smallestValue);
        List<AverageAmount> largeAndSmall = Arrays.asList(largestValue.toAverageAmount(), smallestValue.toAverageAmount());
        return new AverageAmountResponse("외환은행",largeAndSmall);
    }
}
