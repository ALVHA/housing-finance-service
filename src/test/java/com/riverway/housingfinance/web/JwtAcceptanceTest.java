package com.riverway.housingfinance.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtAcceptanceTest extends AcceptanceTest {

    private final Logger log = LoggerFactory.getLogger(JwtAcceptanceTest.class);

    @Test
    public void refreshRequest() {
        String location = registerData();

        String token = createJwt(DEFAULT_USER_ID);
        HttpEntity httpEntity = new HttpEntity(refreshHeader(token));
        ResponseEntity<String> response = requestGet(location + "/banks/years/amount", httpEntity, String.class);

        log.debug("response : {}", response.getHeaders().get("Authorization"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get("Authorization")).isNotEqualTo(token);
    }

    private HttpHeaders refreshHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", "Bearer Token " + token);
        return headers;
    }
}
