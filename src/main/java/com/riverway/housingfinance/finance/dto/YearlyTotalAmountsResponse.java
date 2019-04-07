package com.riverway.housingfinance.finance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class YearlyTotalAmountsResponse {

    private String name;
    private List<YearlyFinanceEachBank> supplyStatus;

    private YearlyTotalAmountsResponse(String name, List<YearlyFinanceEachBank> supplyStatus) {
        this.name = name;
        this.supplyStatus = supplyStatus;
    }

    public static YearlyTotalAmountsResponse of(List<YearlyFinanceEachBank> supplyStatus) {
        return new YearlyTotalAmountsResponse("주택금융 공급현황", supplyStatus);
    }
}
