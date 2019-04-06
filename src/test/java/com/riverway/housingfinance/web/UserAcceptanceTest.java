package com.riverway.housingfinance.web;

import com.riverway.housingfinance.user.UserDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
