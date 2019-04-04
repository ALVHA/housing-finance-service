package com.riverway.housingfinance.service;

import com.riverway.housingfinance.domain.Bank;
import com.riverway.housingfinance.domain.MonthlyFinance;
import com.riverway.housingfinance.domain.SupplyStatusData;
import com.riverway.housingfinance.domain.TotalFinance;
import com.riverway.housingfinance.support.HousingFinancePreprocessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class HousingFinanceService {

    private final HousingFinancePreprocessor preprocessor;
    private final BankService bankService;
    private final MonthlyFinanceService monthlyFinanceService;
    private final YearlyFinanceService yearlyFinanceService;

    public HousingFinanceService(HousingFinancePreprocessor preprocessor
            , BankService bankService
            , MonthlyFinanceService monthlyFinanceService
            , YearlyFinanceService yearlyFinanceService) {
        this.preprocessor = preprocessor;
        this.bankService = bankService;
        this.monthlyFinanceService = monthlyFinanceService;
        this.yearlyFinanceService = yearlyFinanceService;
    }

    public void registerData(MultipartFile file) {
        SupplyStatusData supplyStatusData = preprocessor.read(file);

        List<Bank> banks = bankService.findByNames(supplyStatusData.getBankNames());
        TotalFinance totalFinance = supplyStatusData.convertToMonthlyDataOfBank(banks);
        List<MonthlyFinance> monthlyFinances = totalFinance.getMonthlyFinances();

        log.debug("사이즈: {} ", totalFinance.getYearlyFinances().size());
        totalFinance.getYearlyFinances().values().stream().flatMap(d -> d.values().stream()).forEach(in -> log.debug("결과: {}", in));
        monthlyFinanceService.saveAll(monthlyFinances);
        yearlyFinanceService.saveYearlyFinaces(totalFinance.getYearlyFinances());
    }
}
