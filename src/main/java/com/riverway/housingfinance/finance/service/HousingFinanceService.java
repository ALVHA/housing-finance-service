package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.HousingFinanceSupplyStatus;
import com.riverway.housingfinance.finance.domain.MonthlyFinance;
import com.riverway.housingfinance.finance.domain.YearlyFinance;
import com.riverway.housingfinance.finance.dto.SupplyStatusByYearResponse;
import com.riverway.housingfinance.finance.dto.SupplyStatusData;
import com.riverway.housingfinance.finance.dto.TotalFinance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HousingFinanceService {

    private final MonthlyFinanceService monthlyFinanceService;
    private final YearlyFinanceService yearlyFinanceService;

    public HousingFinanceService(MonthlyFinanceService monthlyFinanceService, YearlyFinanceService yearlyFinanceService) {
        this.monthlyFinanceService = monthlyFinanceService;
        this.yearlyFinanceService = yearlyFinanceService;
    }

    public void registerData(SupplyStatusData supplyStatusData, List<Bank> banks) {
        TotalFinance totalFinance = supplyStatusData.convertToMonthlyDataOfBank(banks);
        List<MonthlyFinance> monthlyFinances = totalFinance.getMonthlyFinances();

        monthlyFinanceService.saveAll(monthlyFinances);
        yearlyFinanceService.saveYearlyFinaces(totalFinance.groupByYearsForTotal());
    }

    public SupplyStatusByYearResponse findSupplyStatus() {
        List<YearlyFinance> yearlyFinances = yearlyFinanceService.findYearlyFinances();
        HousingFinanceSupplyStatus supplyStatus = new HousingFinanceSupplyStatus(yearlyFinances);
        return supplyStatus.toResponse();
    }
}
