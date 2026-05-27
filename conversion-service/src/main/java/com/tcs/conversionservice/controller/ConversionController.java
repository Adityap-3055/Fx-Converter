package com.tcs.conversionservice.controller;

import com.tcs.fxcommon.dto.ConversionResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  Rest api controller for foreign exchange conversions.
 */
@Validated
@RequestMapping("/convert")
public interface ConversionController {

    /**
     * Endpoint to convert a specified amount between two currencies
     * @param source - 3 letter currency annotation to convert money from (e.g: "USD")
     * @param target - 3 letter currency annotation to convert money to (e.g: "INR")
     * @param amount - the amount result after converting including the fee markup
     * @return ConversionResponse that has the applied rate and the final calculated amount.
     */
    @GetMapping
    ResponseEntity<ConversionResponse> convert(
            @RequestParam String source,
            @RequestParam String target,
            @Positive(message = "Amount must be greater than zero")
            @RequestParam double amount
    );

}
