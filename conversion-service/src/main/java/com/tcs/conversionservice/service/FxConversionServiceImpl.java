package com.tcs.conversionservice.service;

import com.tcs.conversionservice.client.PartnerRateClient;
import com.tcs.conversionservice.client.RateResponse;
import com.tcs.conversionservice.exception.CurrencyNotSupportedException;
import com.tcs.fxcommon.dto.ConversionResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * service impl is response for handling foreign exchange currency conversions
 */
@Service
@RefreshScope
public class FxConversionServiceImpl implements FxConversionService{

    private static final Logger log = LoggerFactory.getLogger(FxConversionServiceImpl.class);
    private final PartnerRateClient partnerRateClient;

    @Value("${app.fee.markup}")
    private double feeMarkup;

    public FxConversionServiceImpl(PartnerRateClient partnerRateClient){
        this.partnerRateClient = partnerRateClient;
    }


    // partnerServiceBreaker name is used in yml file for its configurations.

    /**
     * Converts the amount coming from source currency to target currency using
     * partner-rate service and applies the internal fee markup.
     *
     * @param source - 3 letter currency annotation to convert money from (e.g: "USD")
     * @param target - 3 letter currency annotation to convert money to (e.g: "INR")
     * @param amount - the amount result after converting including the fee markup
     * @return ConversionResponse that has the applied rate and the final calculated amount.
     */
    @Retry(name = "partnerServiceRetry", fallbackMethod = "partnerRateFallback")
    @CircuitBreaker(name = "partnerServiceBreaker")
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

    /**
     * Converts the amount coming from source currency to target currency using
     * a default fee and applies the internal fee markup.
     *
     * @param source - 3 letter currency annotation to convert money from (e.g: "USD")
     * @param target - 3 letter currency annotation to convert money to (e.g: "INR")
     * @param amount - the amount result after converting including the fee markup
     * @param throwable - gives the appropriate message for the fallback
     * @return ConversionResponse that has the applied rate and the final calculated amount.
     */
    public ConversionResponse partnerRateFallback(String source, String target, double amount, Throwable throwable){

        // calling in global exception handler
        if (throwable instanceof CurrencyNotSupportedException) {
            throw (CurrencyNotSupportedException) throwable;
        }


        log.warn("Partner service is Unavailable! Triggering fallback. Reason {}", throwable.getMessage());


        // We make hardcoded backup rate value
        double fallbackRawRate = 0.90;
        double finalRate = fallbackRawRate + (fallbackRawRate * feeMarkup);
        double convertedAmount = amount * finalRate;

        return new ConversionResponse(source, target, amount, finalRate, convertedAmount, true);
    }
}
