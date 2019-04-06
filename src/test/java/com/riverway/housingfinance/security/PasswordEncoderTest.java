package com.riverway.housingfinance.security;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void matches() {
        String password = "test123";
        String encoded = passwordEncoder.encode(password);

        assertTrue(passwordEncoder.matches(password, encoded));
    }

    @Test
    public void matches_fail() {
        String password = "test123";
        String encoded = passwordEncoder.encode(password);

        assertFalse(passwordEncoder.matches("test1234", encoded));
    }
}
