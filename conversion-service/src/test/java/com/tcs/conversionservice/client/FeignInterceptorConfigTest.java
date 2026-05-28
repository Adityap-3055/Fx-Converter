package com.tcs.conversionservice.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.*;

class FeignInterceptorConfigTest {

    private final FeignInterceptorConfig config = new FeignInterceptorConfig();

    @BeforeEach
    @AfterEach
    void clearMDC() {
        MDC.clear(); // Ensure MDC is clean before and after tests
    }

    @Test
    void testInterceptorAppliesCorrelationId() {
        // Arrange
        MDC.put("correlationId", "test-uuid-123");
        RequestInterceptor interceptor = config.requestInterceptor();
        RequestTemplate template = new RequestTemplate();

        // Act
        interceptor.apply(template);

        // Assert
        assertTrue(template.headers().containsKey("X-Correlation-ID"));
        assertEquals("test-uuid-123", template.headers().get("X-Correlation-ID").iterator().next());
    }

    @Test
    void testInterceptorDoesNothingWhenNoCorrelationId() {
        // Arrange: Empty MDC
        RequestInterceptor interceptor = config.requestInterceptor();
        RequestTemplate template = new RequestTemplate();

        // Act
        interceptor.apply(template);

        // Assert
        assertFalse(template.headers().containsKey("X-Correlation-ID"));
    }
}