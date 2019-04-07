package com.riverway.housingfinance.finance.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyFinanceSupply {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1)
    @Max(12)
    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int amount;

    public MonthlyFinanceSupply(int month, int amount) {
        this.month = month;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
