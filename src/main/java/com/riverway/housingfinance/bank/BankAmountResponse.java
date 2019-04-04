package com.riverway.housingfinance.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankAmountResponse {

    private String bankName;
    private int amount;
}
