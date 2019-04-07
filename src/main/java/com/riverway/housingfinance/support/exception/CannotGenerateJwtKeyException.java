package com.riverway.housingfinance.support.exception;

public class CannotGenerateJwtKeyException extends RuntimeException {

    public CannotGenerateJwtKeyException(String message) {
        super(message);
    }
}
