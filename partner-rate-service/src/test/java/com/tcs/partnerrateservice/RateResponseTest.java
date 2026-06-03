package com.tcs.partnerrateservice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test constructor, getters and setters
class RateResponseTest {

    @Test
    void testGettersAndSetters() {
        RateResponse response = new RateResponse("USD/INR", 83.5);
        assertEquals("USD/INR", response.getPair());
        assertEquals(83.5, response.getRate());

        response.setPair("EUR/USD");
        response.setRate(1.09);
        assertEquals("EUR/USD", response.getPair());
        assertEquals(1.09, response.getRate());
    }

    @Test
    void testDefaultConstructor() {
        // Some libraries like Jackson need an empty constructor
        RateResponse response = new RateResponse();
        assertNotNull(response);
    }
}