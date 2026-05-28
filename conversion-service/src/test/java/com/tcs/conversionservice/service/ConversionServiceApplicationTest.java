package com.tcs.conversionservice.service;

import com.tcs.conversionservice.ConversionServiceApplication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConversionServiceApplicationTest {

    @Test
    void main() {
        // Act & Assert: Forces JaCoCo to execute the main method lines
        assertDoesNotThrow(() -> {
            ConversionServiceApplication.main(new String[]{
                    "--spring.profiles.active=test",
                    "--server.port=0"
            });
        });
    }
}