package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import com.riverway.housingfinance.finance.domain.repository.YearlyFinanceRepository;
import com.riverway.housingfinance.finance.dto.BankAmount;
import com.riverway.housingfinance.finance.dto.YearlyFinanceEachBank;
import com.riverway.housingfinance.finance.dto.YearlyTotalAmountsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class YearlyTotalAmountsService {

    @Autowired
    private final YearlyFinanceRepository repository;

    public YearlyTotalAmountsService(YearlyFinanceRepository repository) {
        this.repository = repository;
    }

    public YearlyTotalAmountsResponse tally(Integer id) {
        List<YearlyFinanceSupply> supplyStatus = repository.findYearlyFinances(id);
        List<YearlyFinanceEachBank> result = convert(supplyStatus);
        return YearlyTotalAmountsResponse.of(result);
    }

    public List<YearlyFinanceEachBank> convert(List<YearlyFinanceSupply> supplyStatus) {
        Map<Integer, List<YearlyFinanceSupply>> total = groupByYear(supplyStatus);
        return total.keySet().stream()
                .map(year -> {
                    List<BankAmount> bankAmounts = mapToBankAmount(total.get(year));
                    return new YearlyFinanceEachBank(year, bankAmounts);
                })
                .collect(Collectors.toList());
    }

    public Map<Integer, List<YearlyFinanceSupply>> groupByYear(List<YearlyFinanceSupply> yearlyFinanceSupplies) {
        return yearlyFinanceSupplies.stream()
                .collect(Collectors.groupingBy(YearlyFinanceSupply::getYear));
    }

    public List<BankAmount> mapToBankAmount(List<YearlyFinanceSupply> yearlyFinanceSupplies) {
        return yearlyFinanceSupplies.stream()
                .map(YearlyFinanceSupply::toBankAmount)
                .collect(Collectors.toList());
    }

}
