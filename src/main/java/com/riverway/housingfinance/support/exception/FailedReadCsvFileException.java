package com.riverway.housingfinance.support.exception;

public class FailedReadCsvFileException extends RuntimeException {

    public FailedReadCsvFileException() {
    }

    public FailedReadCsvFileException(String message) {
        super(message);
    }
}
