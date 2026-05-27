package com.tcs.fxcommon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for currency conversion requests.
 * Contains conversion details and exchange rate information.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionResponse {

    private String source;
    private String target;
    private double amount;
    private double exchangeRate;
    private double convertedAmount;
    private boolean isStaleDate;        // will be used for Resilience4j fallback requirement

}
