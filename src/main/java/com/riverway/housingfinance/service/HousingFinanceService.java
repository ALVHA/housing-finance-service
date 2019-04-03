package com.riverway.housingfinance.service;

import com.riverway.housingfinance.domain.Bank;
import com.riverway.housingfinance.domain.MonthlyFinance;
import com.riverway.housingfinance.domain.SupplyStatusData;
import com.riverway.housingfinance.support.HousingFinancePreprocessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class HousingFinanceService {

    private final HousingFinancePreprocessor preprocessor;
    private final BankService bankService;
    private final MonthlyFinanceService monthlyFinanceService;

    public HousingFinanceService(HousingFinancePreprocessor preprocessor, BankService bankService, MonthlyFinanceService monthlyFinanceService) {
        this.preprocessor = preprocessor;
        this.bankService = bankService;
        this.monthlyFinanceService = monthlyFinanceService;
    }

    public void registerData(MultipartFile file) {
        SupplyStatusData supplyStatusData = preprocessor.read(file);

        List<Bank> banks = bankService.findByNames(supplyStatusData.getBankNames());
        List<MonthlyFinance> monthlyFinances = supplyStatusData.convertToMonthlyDataOfBank(banks);
        monthlyFinanceService.saveAll(monthlyFinances);
    }
}
