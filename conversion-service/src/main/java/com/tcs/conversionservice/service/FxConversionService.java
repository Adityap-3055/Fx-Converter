package com.tcs.conversionservice.service;

import com.tcs.conversionservice.client.PartnerRateClient;
import com.tcs.conversionservice.client.RateResponse;
import com.tcs.fxcommon.dto.ConversionResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class FxConversionService {

    private static final Logger log = LoggerFactory.getLogger(FxConversionService.class);
    private final PartnerRateClient partnerRateClient;

    @Value("${app.fee.markup}")
    private double feeMarkup;

    public FxConversionService(PartnerRateClient partnerRateClient){
        this.partnerRateClient = partnerRateClient;
    }


    // partnerServiceBreaker name is used in yml file for its configurations.
    @CircuitBreaker(name = "partnerServiceBreaker", fallbackMethod = "partnerRateFallback")
    public ConversionResponse convert(String source, String target, double amount){

        log.info("Calling partner-rate-service for {} to {}", source, target);

        // fetch rate from partner service
        RateResponse rateResponse = partnerRateClient.getRate(source, target);
        double rawRate = rateResponse.getRate();

        // apply fee markup and calculate final amount
        double finalRate = rawRate + ( rawRate * feeMarkup);
        double convertedAmount = amount * finalRate;

        // return the calculate amount response from fx-common(with isStaleData = false)
        return new ConversionResponse(source, target, amount, finalRate, convertedAmount, false);
    }


    // This runs automatically when partnerRateClient.getRate() fails or times out
    public ConversionResponse partnerRateFallback(String source, String target, double amount, Throwable throwable){

        log.warn("Partner service is Unavailable! Triggering fallback. Reason {}", throwable.getMessage());


        // We make hardcoded backup rate value
        double fallbackRawRate = 0.90;
        double finalRate = fallbackRawRate + (fallbackRawRate * feeMarkup);
        double convertedAmount = amount * finalRate;

        return new ConversionResponse(source, target, amount, finalRate, convertedAmount, true);
    }
}
