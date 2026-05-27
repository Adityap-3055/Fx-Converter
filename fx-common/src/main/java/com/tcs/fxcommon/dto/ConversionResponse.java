package com.tcs.fxcommon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for currency conversion requests.
 * Contains conversion details and exchange rate information.
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionResponse {

    private String source;
    private String target;
    private double amount;
    private double exchangeRate;
    private double convertedAmount;
    private boolean isStaleDate;        // will be used for Resilience4j fallback requirement

    public ConversionResponse() {
    }


    public ConversionResponse(String source, String target, double amount, double exchangeRate, double convertedAmount, boolean isStaleDate) {
        this.source = source;
        this.target = target;
        this.amount = amount;
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
        this.isStaleDate = isStaleDate;
    }


    public String getSource() { return source; }
    public String getTarget() { return target; }
    public double getAmount() { return amount; }
    public double getExchangeRate() { return exchangeRate; }
    public double getConvertedAmount() { return convertedAmount; }
    public boolean isStaleDate() { return isStaleDate; }
}
