package com.riverway.housingfinance.finance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//TODO Response 객체에 기본 생성자 필요한지?
@Getter
@NoArgsConstructor
public class SupplyStatusByYearResponse {

    private String name;
    private List<YearlyFinanceBanksResponse> supplyStatus;

    private SupplyStatusByYearResponse(String name, List<YearlyFinanceBanksResponse> supplyStatus) {
        this.name = name;
        this.supplyStatus = supplyStatus;
    }

    public static SupplyStatusByYearResponse of(List<YearlyFinanceBanksResponse> supplyStatus) {
        return new SupplyStatusByYearResponse("주택금융 공급현황", supplyStatus);
    }
}
