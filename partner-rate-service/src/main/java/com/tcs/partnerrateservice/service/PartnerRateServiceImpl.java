    package com.tcs.partnerrateservice.service;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Service;
    import org.springframework.web.server.ResponseStatusException;

    /**
     * Service interface responsible for providing mock foreign exchange rates.
     */
    @Service
    public class PartnerRateServiceImpl implements PartnerRateService {

        private static final Logger log = LoggerFactory.getLogger(PartnerRateServiceImpl.class);

        /**
         * Fetches the exchange rate for the specified currency pair.
         *
         * @param pair The formatted currency pair string (e.g., "USD/EUR")
         * @return The exchange rate as a double
         */
        @Override
        public double fetchRateForPair(String pair) {
            log.info("Partner Service looking up rate for pair: {}", pair);

            // This is the logic moved from your original controller
            return switch (pair) {
                case "USD/INR" -> 83.50;
                case "INR/USD" -> 0.012;
                case "USD/EUR" -> 0.92;
                case "EUR/USD" -> 1.09;
                case "GBP/USD" -> 1.25;
                default -> throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Currency pairs not supported by the partner service: " + pair
                ); // Fallback for unsupported pairs
            };
        }
    }