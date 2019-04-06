package com.riverway.housingfinance.domain;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.Bank;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BankNameTest {

    @Test
    public void convertBankName_test() {
        String bankName = "우리은행";
        assertThat(BankName.of(bankName)).isEqualTo(BankName.WOORI);
    }

    @Test
    public void match_test() {
        List<Bank> banks = new ArrayList<>(Arrays.asList(new Bank("bnk1001", "외환은행")
                , new Bank("bnk1003", "하나은행"), new Bank("bnk1005", "국민은행")));

        BankName bankName = BankName.KOOKMIN;
        assertThat(bankName.match(banks)).isEqualTo(new Bank("bnk1005", "국민은행"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void match_fail_test() {
        List<Bank> banks = new ArrayList<>(Arrays.asList(new Bank("bnk1001", "외환은행")
                , new Bank("bnk1003", "하나은행"), new Bank("bnk1005", "국민은행")));

        BankName bankName = BankName.SHINHAN;
        bankName.match(banks);
    }
}
