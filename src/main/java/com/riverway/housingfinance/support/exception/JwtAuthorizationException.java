package com.riverway.housingfinance.support.exception;

public class JwtAuthorizationException extends RuntimeException {

    public JwtAuthorizationException(String message) {
        super(message);
    }
}
