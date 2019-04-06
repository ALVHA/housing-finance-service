package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import com.riverway.housingfinance.finance.domain.repository.YearlyFinanceRepository;
import com.riverway.housingfinance.finance.dto.YearlyAverageAmount;
import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class YearlyFinanceService {

    private final YearlyFinanceRepository yearlyFinanceRepository;

    public YearlyFinanceService(YearlyFinanceRepository yearlyFinanceRepository) {
        this.yearlyFinanceRepository = yearlyFinanceRepository;
    }

    //List를 객체로 분리
    public void batch(List<MonthlyFinanceSupply> monthlyData) {
        Map<Integer, Map<Bank, List<MonthlyFinanceSupply>>> annualData = groupMonthlyDataByYear(monthlyData);
        for (Integer year : annualData.keySet()) {
            Map<Bank, List<MonthlyFinanceSupply>> amountByBank = annualData.get(year);
            save(year, amountByBank);
        }
    }

    public Map<Integer, Map<Bank, List<MonthlyFinanceSupply>>> groupMonthlyDataByYear(List<MonthlyFinanceSupply> monthlyFinanceSupplies) {
        return monthlyFinanceSupplies.stream()
                .collect(Collectors.groupingBy(MonthlyFinanceSupply::getYear,
                        Collectors.groupingBy(MonthlyFinanceSupply::getBank)));
    }

    public void save(int year, Map<Bank, List<MonthlyFinanceSupply>> amountByBank) {
        for (Bank bank : amountByBank.keySet()) {
            Integer amount = calculateTotal(amountByBank.get(bank));
            yearlyFinanceRepository.save(new YearlyFinanceSupply(year, amount, bank));
        }
    }

    public int calculateTotal(List<MonthlyFinanceSupply> amountByBank) {
        return amountByBank.stream()
                .mapToInt(MonthlyFinanceSupply::getAmount)
                .sum();
    }

    public YearlyFinanceSupply findLargestOfAll() {
        return yearlyFinanceRepository
                .findLargestOfAll()
                .orElseThrow(EntityNotFoundException::new);
    }

    //은행으로 찾기
    public BankSupportAmountResponse findLargestAndSmallest() {
        YearlyFinanceSupply smallestValue = yearlyFinanceRepository.findMinAmountOfExchange();
        YearlyFinanceSupply largestValue = yearlyFinanceRepository.findMaxAmountOfExchange();
        log.debug("가장 큰값 : {}, 작은값 : {}", largestValue, smallestValue);
        List<YearlyAverageAmount> largeAndSmall = Arrays.asList(smallestValue.toAverageAmount(), largestValue.toAverageAmount());
        return new BankSupportAmountResponse("외환은행", largeAndSmall);
    }
}
