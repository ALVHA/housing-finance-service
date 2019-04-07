package com.riverway.housingfinance.domain;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class YearlyFinanceSupplyTest {

    @Test
    public void calculateTotal() {
        Bank bank = new Bank("bnk1010", "신한은행");
        List<MonthlyFinanceSupply> monthlyData = Arrays.asList(new MonthlyFinanceSupply(1, 250)
                , new MonthlyFinanceSupply(2, 120), new MonthlyFinanceSupply(1, 130));

        YearlyFinanceSupply yearlyFinanceSupply = new YearlyFinanceSupply(2018, bank, monthlyData);

        assertThat(yearlyFinanceSupply.calculateTotal()).isEqualTo(500);
    }
}