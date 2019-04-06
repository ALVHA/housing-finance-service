package com.riverway.housingfinance.finance.dto;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class SupplyStatusDataTest {

    private List<BankName> bankNames;
    private List<Bank> banks;

    @Before
    public void setUp() {
        bankNames = new ArrayList<>(Arrays.asList(BankName.HANA, BankName.EXCHANGE, BankName.KOOKMIN));
        banks = new ArrayList<>(Arrays.asList(new Bank("bnk1001", "외환은행")
                , new Bank("bnk1003", "하나은행"), new Bank("bnk1005", "국민은행")));
    }

    @Test
    public void parse_test() {
        List<String> body = new ArrayList<>(Arrays.asList("2018,1,20,300,66"));

        SupplyStatusData supplyStatusData = new SupplyStatusData(bankNames, body);
        List<MonthlyFinanceSupply> monthlyData = supplyStatusData.parse(banks);

        assertThat(monthlyData.size()).isEqualTo(3);
        assertThat(monthlyData.get(0).getBank().getInstituteName()).isEqualTo("하나은행");
        assertThat(monthlyData.get(0).getAmount()).isEqualTo(20);
    }

    @Test
    public void parse_many_test() {
        List<String> body = new ArrayList<>(Arrays.asList("2018,1,20,300,66", "2016,12,240,512,23", "1994,3,250,7,111"));

        SupplyStatusData supplyStatusData = new SupplyStatusData(bankNames, body);
        List<MonthlyFinanceSupply> monthlyData = supplyStatusData.parse(banks);

        assertThat(monthlyData.size()).isEqualTo(9);
        assertThat(monthlyData.get(4).getBank().getInstituteName()).isEqualTo("외환은행");
        assertThat(monthlyData.get(4).getAmount()).isEqualTo(512);
    }
}