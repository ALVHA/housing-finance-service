package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.BankAmountResponse;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinance;
import com.riverway.housingfinance.finance.domain.YearlyFinance;
import com.riverway.housingfinance.finance.support.SupplyStatusByYearResponse;
import com.riverway.housingfinance.finance.support.SupplyStatusData;
import com.riverway.housingfinance.finance.support.TotalFinance;
import com.riverway.housingfinance.finance.support.YearlyFinanceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        log.debug("사이즈: {} ", totalFinance.getYearlyFinances().size());
        monthlyFinanceService.saveAll(monthlyFinances);
        yearlyFinanceService.saveYearlyFinaces(totalFinance.getYearlyFinances());
    }

    public SupplyStatusByYearResponse findAll() {
        Map<Integer, List<YearlyFinance>> groupByYears = yearlyFinanceService.findYearlyFinances()
                .stream()
                .collect(Collectors.groupingBy(YearlyFinance::getYear));
        List<YearlyFinanceResponse> yearlyFinanceResponses = new ArrayList<>();
        for (Integer year : groupByYears.keySet()) {
            List<BankAmountResponse> bankAmountResponses = mapToBankAmount(groupByYears.get(year));
            YearlyFinanceResponse yearyFinanceResponse = new YearlyFinanceResponse(year, bankAmountResponses);
            yearlyFinanceResponses.add(yearyFinanceResponse);
        }
        return new SupplyStatusByYearResponse("주택금융 공급현황", yearlyFinanceResponses);
    }

    public List<BankAmountResponse> mapToBankAmount(List<YearlyFinance> yearlyFinances) {
        return yearlyFinances.stream()
                .map(YearlyFinance::toBankAmount)
                .collect(Collectors.toList());
    }
}
