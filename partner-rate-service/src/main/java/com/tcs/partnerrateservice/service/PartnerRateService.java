package com.tcs.partnerrateservice.service;

/**
 * Service interface responsible for providing mock foreign exchange rates.
 */
public interface PartnerRateService {

    /**
     * Fetches the exchange rate for the specified currency pair.
     *
     * @param pair The formatted currency pair string (e.g., "USD/EUR")
     * @return The exchange rate as a double
     */
    double fetchRateForPair(String pair);
}