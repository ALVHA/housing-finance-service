package com.riverway.housingfinance.exception;

public class FailedReadCsvFile extends RuntimeException {

    public FailedReadCsvFile() {
    }

    public FailedReadCsvFile(String message) {
        super(message);
    }
}
