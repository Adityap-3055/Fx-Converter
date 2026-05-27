package com.tcs.fxcommon.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standard error response DTO for API failures.
 * Sent to clients with HTTP error status and details.
 * */
@Data
@NoArgsConstructor
public class ErrorResponse {

    private String timestamp;
    private int status;     // HTTP status code (400, 404, 500...)
    private String error;
    private String message;
    private String path;

    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
