package com.riverway.housingfinance.support.exception;

public class CannotGenrateJwtKeyException extends RuntimeException{

    public CannotGenrateJwtKeyException(String message) {
        super(message);
    }
}
