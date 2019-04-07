package com.riverway.housingfinance.domain;

import com.riverway.housingfinance.support.exception.ErrorMessage;
import com.riverway.housingfinance.support.exception.UnAuthenticationException;
import com.riverway.housingfinance.user.domain.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertTrue;

public class UserTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void matchPassword() {
        String encodedPassword = passwordEncoder.encode("1234");
        User user = new User("user1234", encodedPassword);

        assertTrue(user.matchPassword("1234", passwordEncoder));
    }

    @Test
    public void matchPassword_fail() {
        String encodedPassword = passwordEncoder.encode("1234");
        User user = new User("user1234", encodedPassword);

        thrown.expect(UnAuthenticationException.class);
        thrown.expectMessage(ErrorMessage.WRONG_PASSWORD);

        user.matchPassword("123456", passwordEncoder);
    }
}
