package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.bank.service.BankService;
import com.riverway.housingfinance.finance.support.SupplyStatusData;
import com.riverway.housingfinance.finance.support.TotalFinance;
import com.riverway.housingfinance.finance.domain.MonthlyFinance;
import com.riverway.housingfinance.finance.support.CsvFilePreprocessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class HousingFinanceService {

    private final CsvFilePreprocessor preprocessor;
    private final BankService bankService;
    private final MonthlyFinanceService monthlyFinanceService;
    private final YearlyFinanceService yearlyFinanceService;

    public HousingFinanceService(CsvFilePreprocessor preprocessor
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
