package com.tcs.conversionservice.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateResponseTest {

    @Test
    void testConstructorAndGetters() {

        RateResponse response = new RateResponse("USD/INR", 83.5);

        assertEquals("USD/INR", response.getPair());
        assertEquals(83.5, response.getRate());
    }

    @Test
    void testDefaultConstructor() {

        RateResponse response = new RateResponse();

        assertNotNull(response);
    }
}