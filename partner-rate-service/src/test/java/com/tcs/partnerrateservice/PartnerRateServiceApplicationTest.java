package com.tcs.partnerrateservice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PartnerRateServiceApplicationTest {

    @Test
    void main() {
        
        assertDoesNotThrow(() -> {
            PartnerRateServiceApplication.main(new String[]{
                    "--spring.profiles.active=test",
                    "--server.port=0"
            });
        });
    }
}