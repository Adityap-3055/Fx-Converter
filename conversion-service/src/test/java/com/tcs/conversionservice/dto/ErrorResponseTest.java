package com.tcs.fxcommon.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void testConstructorAndGetters() {

        ErrorResponse response =
                new ErrorResponse(
                        400,
                        "Bad Request",
                        "Invalid input",
                        "/convert"
                );

        assertEquals(400, response.getStatus());
        assertEquals("Bad Request", response.getError());
        assertEquals("Invalid input", response.getMessage());
        assertEquals("/convert", response.getPath());
        assertNotNull(response.getTimestamp());
    }
}