package com.riverway.housingfinance.web;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class BankAcceptanceTest extends AcceptanceTest {

    @Test
    public void showFinancialInstitutions_test() {
        ResponseEntity<List> response = template().getForEntity("/api/housing/finance/banks", List.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(9);
    }
}
