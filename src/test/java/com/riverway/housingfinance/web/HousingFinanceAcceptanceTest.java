package com.riverway.housingfinance.web;

import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import com.riverway.housingfinance.finance.dto.YearlyTotalAmountsResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@Slf4j
public class HousingFinanceAcceptanceTest extends AcceptanceTest {

    @Test
    public void uploadCsvFile_test() {
        HttpEntity<MultiValueMap<String, Object>> request = uploadCsvFileRequest();

        ResponseEntity<String> response = template().postForEntity("/api/housing/finance", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/api/housing/finance"));
    }

    @Test
    public void getYearlyTotalAmountEachBank() {
        registerData();

        ResponseEntity<YearlyTotalAmountsResponse> response = template()
                .getForEntity("/api/housing/finance/banks/yearly/amount", YearlyTotalAmountsResponse.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getSupplyStatus().size()).isEqualTo(13);
    }

    @Test
    public void getLargestAmountOfAll_test() {
        registerData();

        ResponseEntity<LargestAmountResponse> response = template()
                .getForEntity("/api/housing/finance/banks/yearly/amount/largest", LargestAmountResponse.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getYear()).isEqualTo(96184);
        assertThat(response.getBody().getBank()).isEqualTo("주택도시기금");
    }

    @Test
    public void getMaxAndMinValue_외환은행() {
        registerData();

        ResponseEntity<String> response = template()
                .getForEntity("/api/housing/finance/banks/exchange/yearly/amount", String.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
