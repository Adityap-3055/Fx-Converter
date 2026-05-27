package com.tcs.partnerrateservice.controller;

import com.tcs.partnerrateservice.RateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the Mock Partner Exchange Rate Service.
 * This simulates a third-party financial provider.
 */
@RestController
@RequestMapping("/api/partner/v1/rates")
public interface RateController {

    /**
     * Retrieves the current mock exchange rate for a given currency pair.
     *
     * @param source The base currency code (e.g., USD)
     * @param target The target currency code (e.g., EUR)
     * @return RateResponse containing the requested pair and the mock exchange rate
     */
    @GetMapping
    ResponseEntity<RateResponse> getExchangeRate(
            @RequestParam("source") String source,
            @RequestParam("target") String target
    );
}
