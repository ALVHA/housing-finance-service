package com.riverway.housingfinance.support;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import com.riverway.housingfinance.finance.support.HousingFinanceFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class HousingFinanceFactoryTest {

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

        HousingFinanceFactory housingFinanceFactory = new HousingFinanceFactory(bankNames, body);
        List<YearlyFinanceSupply> yearlyBank = housingFinanceFactory.parse(banks);

        assertThat(yearlyBank.size()).isEqualTo(3);
        assertThat(yearlyBank.get(0).getYear()).isEqualTo(2018);
    }

    @Test
    public void parse_many_test() {
        List<String> body = new ArrayList<>(Arrays.asList("2018,1,20,300,66", "2016,12,240,512,23", "1994,3,250,7,111", "2016,1,520,12,125"));

        HousingFinanceFactory housingFinanceFactory = new HousingFinanceFactory(bankNames, body);
        List<YearlyFinanceSupply> yearlyBank = housingFinanceFactory.parse(banks);

        //연도별 은행의 금융 지원금액 - 3개년도 * 3개의 은행
        assertThat(yearlyBank.size()).isEqualTo(9);
    }
}