package com.tcs.conversionservice.exception;

import com.tcs.fxcommon.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void testHandleCurrencyNotFound() {

        CurrencyNotSupportedException ex =
                new CurrencyNotSupportedException("Invalid pair");

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/convert");

        ErrorResponse response =
                handler.handleCurrencyNotFound(ex, request).getBody();

        assertNotNull(response);
        assertEquals(400, response.getStatus());
    }

    @Test
    void testHandleValidationErrors() {

        ConstraintViolationException ex =
                new ConstraintViolationException("Invalid amount", null);

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/convert");

        ErrorResponse response =
                handler.handleValidationErrors(ex, request).getBody();

        assertNotNull(response);
        assertEquals(400, response.getStatus());
    }
}