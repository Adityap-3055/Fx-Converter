package com.tcs.conversionservice.client;

import com.tcs.conversionservice.exception.CurrencyNotSupportedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

/**
 * Custom Feign Error Decoder to handle specific HTTP status codes from the Partner Service.
 */
@Component
public class FeignCustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404) {
            return new CurrencyNotSupportedException("The requested currency pair is not supported.");
        }

        // For 500s or timeouts Feign will do circuit breaking.
        return defaultErrorDecoder.decode(methodKey, response);
    }
}