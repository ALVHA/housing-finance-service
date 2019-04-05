package com.riverway.housingfinance.finance;

public class FailedReadCsvFileException extends RuntimeException {

    public FailedReadCsvFileException() {
    }

    public FailedReadCsvFileException(String message) {
        super(message);
    }
}
