package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import com.riverway.housingfinance.finance.domain.repository.YearlyFinanceRepository;
import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import com.riverway.housingfinance.finance.dto.YearlyAverageAmount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Slf4j
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

    public BankSupportAmountResponse findLargestAndSmallest(Integer id, String bankId) {
        log.debug("뱅크 아이디:{}", bankId);
        YearlyFinanceSupply smallestValue = yearlyFinanceRepository.findMinAmountOfExchange(id, bankId);
        YearlyFinanceSupply largestValue = yearlyFinanceRepository.findMaxAmountOfExchange(id, bankId);
        log.debug("가장 큰값 : {}, 작은값 : {}", largestValue, smallestValue);
        List<YearlyAverageAmount> largeAndSmall = Arrays.asList(smallestValue.toAverageAmount(), largestValue.toAverageAmount());
        return new BankSupportAmountResponse("외환은행", largeAndSmall);
    }
}
