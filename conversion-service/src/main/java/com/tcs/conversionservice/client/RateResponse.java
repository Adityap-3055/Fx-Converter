package com.tcs.conversionservice.client;

import lombok.Data;

/**
 * Because the partner-rate-service sends back a JSON response like
 * {"pair": "USD/EUR", "rate": 0.92}, our Feign client needs an object to map that data into.
 */

/**
 * To create structured response throughout the application
 */
@Data
public class RateResponse {

    private String pair;
    private double rate;
}


