package com.riverway.housingfinance.support;

import com.riverway.housingfinance.domain.BankName;
import com.riverway.housingfinance.domain.SupplyStatusData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
        InputStream inputStream = new FileInputStream(csv);
        int csvFileRows = 154;              //title 부분을 제외한 row 수
        int numberOfBanks = 9;

        SupplyStatusData supplyStatusData = preprocessor.read(inputStream);
        List<String> rows = supplyStatusData.getRows();
        List<BankName> bankNames = supplyStatusData.getBankNames();

        assertThat(rows.size()).isEqualTo(csvFileRows);
        assertThat(bankNames.size()).isEqualTo(numberOfBanks);
    }

    @Test
    public void cleanseData_test_before_2016() {
        String dataRow = "2007,7,1996,444,88,147,10,156,86,106,55,,,,,,,";
        String[] cleansedData = {"2007", "7", "1996", "444", "88", "147", "10", "156", "86", "106", "55"};

        assertThat(preprocessor.cleanseData(dataRow)).isEqualTo(cleansedData);
    }

    @Test
    public void cleanseData_test_after_2016() {
        String dataRow = "2016,11,\"7,920\",\"3,257\",\"4,078\",\"3,358\",3,\"6,168\",\"1,472\",0,\"11,569\",,,,,,,";
        String cleansedData = "2016,11,7920,3257,4078,3358,3,6168,1472,0,11569,,,,,,,";

        assertThat(preprocessor.verifyBaseYear(dataRow)).isEqualTo(cleansedData);
    }

    @Test
    public void removeCommaBetweenDoubleQuotes_test() {
        String data = "2017,10,\"8,354\",\"2,995\",\"4,384\",\"4,518\",0,\"1,987\",\"1,436\",0,\"2,186\",,,,,,,";
        String completedData = "2017,10,8354,2995,4384,4518,0,1987,1436,0,2186,,,,,,,";

        assertThat(preprocessor.removeCommaBetweenDoubleQuotes(data)).isEqualTo(completedData);
    }

    @Test
    public void parseTitle_test() {
        String title = "연도,월,주택도시기금1)(억원),국민은행(억원),우리은행(억원),신한은행(억원)," +
                "한국시티은행(억원),하나은행(억원),농협은행/수협은행(억원),외환은행(억원),기타은행(억원),,,,,,,";

        List<BankName> bankNames = preprocessor.parseTitle(title);
        log.debug("은행 : {}", bankNames);

        assertThat(bankNames.size()).isEqualTo(9);
        assertThat(bankNames.get(5)).isEqualTo(BankName.HANA);
    }

    @Test
    public void filterEmptyDataToInt_test() {
        String data = "2015,1,5065,3335,3731,2675,4,1894,1272,991,2808,,,,,,,";
        int[] filterdData = {2015, 1, 5065, 3335, 3731, 2675, 4, 1894, 1272, 991, 2808};

        assertThat(preprocessor.filterEmptyDataToInt(data)).isEqualTo(filterdData);
    }
}
