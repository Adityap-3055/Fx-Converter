package com.tcs.conversionservice.client;

import com.tcs.conversionservice.exception.CurrencyNotSupportedException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FeignCustomErrorDecoderTest {

    private final FeignCustomErrorDecoder decoder = new FeignCustomErrorDecoder();

    @Test
    void testDecode_404_ReturnsCustomException() {
        // 404 response from Feign
        Response response = Response.builder()
                .status(404)
                .reason("Not Found")
                .request(Request.create(Request.HttpMethod.GET, "/api", new HashMap<>(), null, Charset.defaultCharset(), null))
                .build();

        // Act
        Exception exception = decoder.decode("methodKey", response);

        // Assert: Ensure it translated to our custom exception
        assertTrue(exception instanceof CurrencyNotSupportedException);
        assertEquals("The requested currency pair is not supported.", exception.getMessage());
    }

    @Test
    void testDecode_500_ReturnsDefaultException() {
        // Arrange: Simulate a 500 response
        Response response = Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .request(Request.create(Request.HttpMethod.GET, "/api", new HashMap<>(), null, Charset.defaultCharset(), null))
                .build();

        // Act
        Exception exception = decoder.decode("methodKey", response);

        // Assert: Ensure it falls back to the default decoder
        assertFalse(exception instanceof CurrencyNotSupportedException);
    }
}