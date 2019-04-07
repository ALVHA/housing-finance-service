package com.riverway.housingfinance.web;

import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import com.riverway.housingfinance.finance.dto.YearlyAverageAmount;
import com.riverway.housingfinance.finance.dto.YearlyTotalAmountsResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import java.util.List;

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

        ResponseEntity<YearlyTotalAmountsResponse> response = requestGet("/api/housing/finance/banks/yearly/amount", jwtEntity(), YearlyTotalAmountsResponse.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getSupplyStatus().size()).isEqualTo(13);
    }

    @Test
    public void getLargestAmountOfAll_test() {
        registerData();

        ResponseEntity<LargestAmountResponse> response = requestGet("/api/housing/finance/banks/yearly/amount/largest", jwtEntity(), LargestAmountResponse.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getYear()).isEqualTo(2014);
        assertThat(response.getBody().getBank()).isEqualTo("주택도시기금");
    }

    @Test
    public void getMaxAndMinValue_외환은행() {
        registerData();

        ResponseEntity<BankSupportAmountResponse> response = requestGet("/api/housing/finance/banks/exchange/yearly/amount", jwtEntity(), BankSupportAmountResponse.class);
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
}
