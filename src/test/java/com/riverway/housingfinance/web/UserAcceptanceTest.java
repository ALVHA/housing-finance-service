package com.riverway.housingfinance.web;

import com.riverway.housingfinance.user.dto.UserDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest extends AcceptanceTest {

    private final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Test
    public void register() {
        UserDto user = createUser("testUser");
        ResponseEntity<UserDto> response = template().postForEntity("/api/users", user, UserDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(user);
    }

    @Test
    public void register_invaild() {
        UserDto user = createUser("like");
        ResponseEntity<UserDto> response = template().postForEntity("/api/users", user, UserDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void login() {
        UserDto user = registerUser("testUser2");
        ResponseEntity<UserDto> response = template().postForEntity("/api/users/auth", user, UserDto.class);

        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get("Authorization")).isNotNull();
    }

    @Test
    public void login_fail() {
        UserDto user = registerUser("testUser3");
        user.setPassword("1234");
        ResponseEntity<UserDto> response = template().postForEntity("/api/users/auth", user, UserDto.class);

        log.debug("response : {}", response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
