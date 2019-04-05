package com.riverway.housingfinance.finance.domain;

import com.riverway.housingfinance.bank.BankAmount;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.dto.YearlyAverageAmount;
import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

//TODO tostring 지우기
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YearlyFinanceSupply {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    public YearlyFinanceSupply(int year, int amount, Bank bank) {
        this.year = year;
        this.amount = amount;
        this.bank = bank;
    }

    public int getYear() {
        return year;
    }

    public BankAmount toBankAmount () {
        return new BankAmount(bank.getInstituteName(), amount);
    }

    public LargestAmountResponse toLargestAmount() {
        return new LargestAmountResponse(year, bank.getInstituteName());
    }

    public YearlyAverageAmount toAverageAmount() {
        return new YearlyAverageAmount(year, amount);
    }
}
