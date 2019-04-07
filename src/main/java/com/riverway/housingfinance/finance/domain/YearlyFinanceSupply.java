package com.riverway.housingfinance.finance.domain;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.dto.BankAmount;
import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import com.riverway.housingfinance.finance.dto.YearlyAverageAmount;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
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

    @ManyToOne
    @JoinColumn(name = "housing_finance_id")
    private HousingFinanceFile housingFinance;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "yearly_finance_supply_id")
    private List<MonthlyFinanceSupply> monthlyFinanceSupplies;

    public YearlyFinanceSupply(int year, Bank bank, List<MonthlyFinanceSupply> monthlyFinanceSupplies) {
        this.year = year;
        this.bank = bank;
        this.monthlyFinanceSupplies = monthlyFinanceSupplies;
        this.amount = calculateTotal();
    }

    public int calculateTotal() {
        return monthlyFinanceSupplies.stream()
                .mapToInt(MonthlyFinanceSupply::getAmount)
                .sum();
    }

    public int getYear() {
        return year;
    }

    public BankAmount toBankAmount() {
        return new BankAmount(bank.getInstituteName(), amount);
    }

    public LargestAmountResponse toLargestAmount() {
        return new LargestAmountResponse(year, bank.getInstituteName());
    }

    public YearlyAverageAmount toAverageAmount() {
        return new YearlyAverageAmount(year, amount);
    }
}
