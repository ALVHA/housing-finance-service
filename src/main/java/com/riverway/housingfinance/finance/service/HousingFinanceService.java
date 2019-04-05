package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import com.riverway.housingfinance.finance.dto.SupplyStatusData;
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
        List<MonthlyFinanceSupply> monthlyFinanceSupplies = supplyStatusData.parse(banks);

        monthlyFinanceService.saveAll(monthlyFinanceSupplies);
        yearlyFinanceService.batch(monthlyFinanceSupplies);
    }

    public LargestAmountResponse findLargestOfAll() {
        return yearlyFinanceService.findLargestOfAll().toLargestAmount();
    }

    public BankSupportAmountResponse findLargestAndSmallest() {
        return yearlyFinanceService.findLargestAndSmallest();
    }
}
