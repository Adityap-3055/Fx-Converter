package com.tcs.conversionservice.exception;

public class CurrencyNotSupportedException extends RuntimeException {
    public CurrencyNotSupportedException(String message) {
        super(message);
    }
}