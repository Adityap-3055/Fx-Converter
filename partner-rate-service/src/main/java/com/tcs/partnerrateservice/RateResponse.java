package com.tcs.partnerrateservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * To send structured response back to conversion service
 */

public class RateResponse {

    private String pair;
    private double rate;

    public RateResponse(String pair, double rate) {
        this.pair = pair;
        this.rate = rate;
    }

    public RateResponse() {
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }
}
