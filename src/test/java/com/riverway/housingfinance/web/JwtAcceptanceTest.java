package com.riverway.housingfinance.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.*;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class JwtAcceptanceTest extends AcceptanceTest {

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
