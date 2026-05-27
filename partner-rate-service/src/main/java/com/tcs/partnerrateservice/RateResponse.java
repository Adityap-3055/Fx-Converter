package com.tcs.partnerrateservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * To send structured response back to conversion service
 */
@Data
public class RateResponse {

    private String pair;
    private double rate;

    public RateResponse(String pair, double rate) {
        this.pair = pair;
        this.rate = rate;
    }
}
