package com.riverway.housingfinance.domain;

import com.riverway.housingfinance.finance.domain.HousingFinanceFile;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class HousingFinanceFileTest {

    @Test
    public void parseExtention() {
        String name = HousingFinanceFile.parseExtention("데이터 분석.txt");
        assertThat(name).isEqualTo(".txt");
    }

    @Test
    public void convert() {
        String name = HousingFinanceFile.convert("데이터 분석.csv");
        assertThat(name.length()).isEqualTo(32 + 4);
    }
}