package com.tcs.conversionservice.service;

import com.tcs.fxcommon.dto.ConversionResponse;

/**
 * service interface is response for handling foreign exchange currency conversions
 */
public interface FxConversionService {

    /**
     * Converts the amount coming from source currency to target currency using
     * partner-rate service fee and applies the internal fee markup.
     *
     * @param source - 3 letter currency annotation to convert money from (e.g: "USD")
     * @param target - 3 letter currency annotation to convert money to (e.g: "INR")
     * @param amount - the amount result after converting including the fee markup
     * @return ConversionResponse that has the applied rate and the final calculated amount.
     */
    public ConversionResponse convert(String source, String target, double amount);
}
