package com.riverway.housingfinance.domain;

import com.riverway.housingfinance.bank.BankName;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BankNameTest {

    @Test
    public void convertBankName_test() {
        String bankName = "우리은행";
        assertThat(BankName.of(bankName)).isEqualTo(BankName.WOORI);
    }
}
