package com.riverway.housingfinance.service;

import com.riverway.housingfinance.domain.Bank;
import com.riverway.housingfinance.domain.MonthlyFinance;
import com.riverway.housingfinance.domain.MonthlyFinanceRepository;
import com.riverway.housingfinance.domain.SupplyStatusData;
import com.riverway.housingfinance.support.HousingFinancePreprocessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class HousingFinanceService {

    private final HousingFinancePreprocessor preprocessor;
    private final BankService bankService;
    private final MonthlyFinanceRepository monthlyFinanceRepository;

    public HousingFinanceService(HousingFinancePreprocessor preprocessor, BankService bankService, MonthlyFinanceRepository monthlyFinanceRepository) {
        this.preprocessor = preprocessor;
        this.bankService = bankService;
        this.monthlyFinanceRepository = monthlyFinanceRepository;
    }

    public void registerData(InputStream in) throws IOException {
        SupplyStatusData supplyStatusData = preprocessor.read(in);

        List<Bank> banks = bankService.findByNames(supplyStatusData.getBankNames());
        List<MonthlyFinance> monthlyFinances = supplyStatusData.convertToMonthlyDataOfBank(banks);
        monthlyFinanceRepository.saveAll(monthlyFinances);
        log.debug("dwq {}", banks.size());
    }
}
