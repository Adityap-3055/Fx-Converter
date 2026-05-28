package com.tcs.conversionservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyNotSupportedExceptionTest {

    @Test
    void testExceptionMessage() {

        CurrencyNotSupportedException ex =
                new CurrencyNotSupportedException("Invalid Currency");

        assertEquals("Invalid Currency", ex.getMessage());
    }
}