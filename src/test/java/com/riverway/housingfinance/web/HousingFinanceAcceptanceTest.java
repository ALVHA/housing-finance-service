package com.riverway.housingfinance.web;

import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import com.riverway.housingfinance.finance.dto.YearlyAverageAmount;
import com.riverway.housingfinance.finance.dto.YearlyTotalAmountsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class HousingFinanceAcceptanceTest extends AcceptanceTest {

    private final Logger log = LoggerFactory.getLogger(HousingFinanceAcceptanceTest.class);

    @Test
    public void uploadCsvFile() {
        HttpEntity<MultiValueMap<String, Object>> request = uploadCsvFileRequestWithToken();

        ResponseEntity<String> response = template().postForEntity("/api/housing/finance", request, String.class);
        log.debug("response : {}", response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/api/housing/finance"));
    }

    @Test
    public void uploadCsvFile_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = uploadCsvFileRequest();

        ResponseEntity<String> response = template().postForEntity("/api/housing/finance", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void getYearlyTotalAmountEachBank() {
        String location = registerData();

        ResponseEntity<YearlyTotalAmountsResponse> response = requestGet(location + "/banks/years/amount", jwtEntity(), YearlyTotalAmountsResponse.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getSupplyStatus().size()).isEqualTo(13);
    }

    @Test
    public void getYearlyTotalAmountEachBank_no_login() {
        String location = registerData();

        ResponseEntity<YearlyTotalAmountsResponse> response = template().getForEntity(location + "/banks/years/amount", YearlyTotalAmountsResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void getLargestAmountOfAll() {
        String location = registerData();

        ResponseEntity<LargestAmountResponse> response = requestGet(location + "/banks/years/amount/largest", jwtEntity(), LargestAmountResponse.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getYear()).isEqualTo(2014);
        assertThat(response.getBody().getBank()).isEqualTo("주택도시기금");
    }

    @Test
    public void getMaxAndMinValue_외환은행() {
        String location = registerData();

        ResponseEntity<BankSupportAmountResponse> response = requestGet(location + "/banks/exchange/years/amount", jwtEntity(), BankSupportAmountResponse.class);
        log.debug("response : {}", response);

        List<YearlyAverageAmount> data = response.getBody().getSupportAmount();
        YearlyAverageAmount smallest = data.get(0);
        YearlyAverageAmount largest = data.get(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(smallest.getYear()).isEqualTo(2008);
        assertThat(smallest.getAmount()).isEqualTo(78);
        assertThat(largest.getYear()).isEqualTo(2015);
        assertThat(largest.getAmount()).isEqualTo(1702);
    }

    @Test
    public void getMaxAndMinValue_하나은행() {
        String location = registerData();

        ResponseEntity<BankSupportAmountResponse> response = requestGet(location + "/banks/hana/years/amount", jwtEntity(), BankSupportAmountResponse.class);
        log.debug("response : {}", response);

        List<YearlyAverageAmount> data = response.getBody().getSupportAmount();
        YearlyAverageAmount smallest = data.get(0);
        YearlyAverageAmount largest = data.get(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(smallest.getYear()).isEqualTo(2009);
        assertThat(smallest.getAmount()).isEqualTo(102);
        assertThat(largest.getYear()).isEqualTo(2016);
        assertThat(largest.getAmount()).isEqualTo(3790);
    }
}
