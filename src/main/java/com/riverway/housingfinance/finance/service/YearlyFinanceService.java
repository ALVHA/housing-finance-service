package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import com.riverway.housingfinance.finance.domain.repository.YearlyFinanceRepository;
import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import com.riverway.housingfinance.finance.dto.YearlyAverageAmount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
public class YearlyFinanceService {

    private final YearlyFinanceRepository yearlyFinanceRepository;

    public YearlyFinanceService(YearlyFinanceRepository yearlyFinanceRepository) {
        this.yearlyFinanceRepository = yearlyFinanceRepository;
    }

    public YearlyFinanceSupply findLargestOfAll(Integer id) {
        return yearlyFinanceRepository
                .findLargestOfAll(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public BankSupportAmountResponse findLargestAndSmallest(Integer id, Bank bank) {
        String bankId = bank.getInstituteCode();
        YearlyFinanceSupply smallestValue = yearlyFinanceRepository.findMinAmountOfExchange(id, bankId);
        YearlyFinanceSupply largestValue = yearlyFinanceRepository.findMaxAmountOfExchange(id, bankId);
        List<YearlyAverageAmount> largeAndSmall = Arrays.asList(smallestValue.toAverageAmount(), largestValue.toAverageAmount());
        return new BankSupportAmountResponse(bank.getInstituteName(), largeAndSmall);
    }
}
