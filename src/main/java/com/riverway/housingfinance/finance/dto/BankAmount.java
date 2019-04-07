package com.riverway.housingfinance.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankAmount {

    private String bankName;
    private int amount;
}
