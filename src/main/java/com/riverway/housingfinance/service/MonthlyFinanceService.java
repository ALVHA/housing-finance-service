package com.riverway.housingfinance.service;

import com.riverway.housingfinance.domain.MonthlyFinance;
import com.riverway.housingfinance.domain.MonthlyFinanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonthlyFinanceService {

    private final MonthlyFinanceRepository monthlyFinanceRepository;

    public MonthlyFinanceService(MonthlyFinanceRepository monthlyFinanceRepository) {
        this.monthlyFinanceRepository = monthlyFinanceRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(List<MonthlyFinance> monthlyFinances) {
        monthlyFinanceRepository.saveAll(monthlyFinances);
    }
}
