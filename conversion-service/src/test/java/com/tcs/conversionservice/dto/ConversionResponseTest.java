package com.tcs.conversionservice.dto;

import com.tcs.fxcommon.dto.ConversionResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConversionResponseTest {

    @Test
    void testConstructorAndGetters() {

        ConversionResponse response =
                new ConversionResponse(
                        "USD",
                        "INR",
                        100,
                        84,
                        8400,
                        false
                );

        assertEquals("USD", response.getSource());
        assertEquals("INR", response.getTarget());
        assertEquals(100, response.getAmount());
        assertEquals(84, response.getExchangeRate());
        assertEquals(8400, response.getConvertedAmount());
        assertFalse(response.isStaleDate());
    }
}