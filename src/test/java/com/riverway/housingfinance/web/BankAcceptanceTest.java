package com.riverway.housingfinance.web;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BankAcceptanceTest extends AcceptanceTest {

    private final Logger log = LoggerFactory.getLogger(BankAcceptanceTest.class);

    @Test
    public void showFinancialInstitutions() {
        ResponseEntity<List> response = requestGet("/api/housing/finance/banks", jwtEntity(), List.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(9);
    }

    @Test
    public void showFinancialInstitutions_no_login() {
        ResponseEntity<List> response = template().getForEntity("/api/housing/finance/banks", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
