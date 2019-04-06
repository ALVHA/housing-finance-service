package com.riverway.housingfinance.web;

import com.riverway.housingfinance.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class UserAcceptanceTest extends AcceptanceTest {

    @Test
    public void register() {
        UserDto user = createUserDefault();
        ResponseEntity<UserDto> response = template().postForEntity("/api/users", user, UserDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(user);
    }

    @Test
    public void login() {
        UserDto user = registerUser("testUser");
        ResponseEntity<UserDto> response = template().postForEntity("/api/users/auth", user, UserDto.class);

        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get("Authorization")).isNotNull();
    }
}
