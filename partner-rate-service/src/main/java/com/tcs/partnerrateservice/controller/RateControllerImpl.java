package com.tcs.partnerrateservice.controller;

import com.tcs.partnerrateservice.RateResponse;
import com.tcs.partnerrateservice.service.PartnerRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the Mock Partner Exchange Rate Service.
 * This simulates a third-party financial provider.
 */
@RestController
public class RateControllerImpl implements RateController{

    private static final Logger log = LoggerFactory.getLogger(RateController.class);
    private final PartnerRateService rateService;

    public RateControllerImpl(PartnerRateService rateService) {
        this.rateService = rateService;
    }

    /**
     * Retrieves the current mock exchange rate for a given currency pair.
     *
     * @param source The base currency code (e.g., USD)
     * @param target The target currency code (e.g., EUR)
     * @return RateResponse containing the requested pair and the mock exchange rate
     */
    @Override
    public ResponseEntity<RateResponse> getExchangeRate(String source, String target) {
        // 1. Format the string
        String pair = source.toUpperCase() + "/" + target.toUpperCase();
        log.info("Partner API received rate request for pair: {}", pair);

        // 2. Ask the service for the rate
        double rate = rateService.fetchRateForPair(pair);

        // 3. Return the response
        return ResponseEntity.ok(new RateResponse(pair, rate));
    }
}