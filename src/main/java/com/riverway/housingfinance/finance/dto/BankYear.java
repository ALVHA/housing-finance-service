package com.riverway.housingfinance.finance.dto;

import com.riverway.housingfinance.bank.domain.Bank;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class BankYear {

    private int year;
    private Bank bank;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankYear bankYear = (BankYear) o;
        return year == bankYear.year &&
                Objects.equals(bank, bankYear.bank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, bank);
    }
}
