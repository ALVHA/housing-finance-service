package com.riverway.housingfinance.web;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

public class JwtAcceptanceTest extends AcceptanceTest {

    @Test
    public void jwtToken() {
        ResponseEntity<String> response = template().getForEntity("/api/test/jwt", String.class);
        System.out.println(response);
    }

}
