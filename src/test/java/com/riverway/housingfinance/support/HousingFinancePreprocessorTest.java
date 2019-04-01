package com.riverway.housingfinance.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class HousingFinancePreprocessorTest {

    private HousingFinancePreprocessor preprocessor;

    @Before
    public void setUp() {
        preprocessor = new HousingFinancePreprocessor();
    }

    @Test
    public void readCsvFile() throws IOException {
        File csv = new ClassPathResource("주택금융신용보증_금융기관별_공급현황.csv").getFile();

//        assertThat(preprocessor.readCsvFile(csv).length).isEqualTo(10);
    }

    @Test
    public void cleanseData_test_before_2016() {
        String dataRow = "2007,7,1996,444,88,147,10,156,86,106,55,,,,,,,";
        int[] cleansedData = {2007,7,1996,444,88,147,10,156,86,106,55};

        assertThat(preprocessor.cleanseData(dataRow)).isEqualTo(cleansedData);
    }

    @Test
    public void cleanseData_test_after_2016() {
        String dataRow = "2016,11,\"7,920\",\"3,257\",\"4,078\",\"3,358\",3,\"6,168\",\"1,472\",0,\"11,569\",,,,,,,";
        int[] cleansedData = {2016,11,7920,3257,4078,3358,3,6168,1472,0,11569};

        assertThat(preprocessor.cleanseData(dataRow)).isEqualTo(cleansedData);
    }

    @Test
    public void removeCommaBetweenDoubleQuotes_test() {
        String data = "2017,10,\"8,354\",\"2,995\",\"4,384\",\"4,518\",0,\"1,987\",\"1,436\",0,\"2,186\",,,,,,,";
        String completedData = "2017,10,8354,2995,4384,4518,0,1987,1436,0,2186,,,,,,,";

        assertThat(preprocessor.removeCommaBetweenDoubleQuotes(data)).isEqualTo(completedData);
    }
}
