package com.tcs.partnerrateservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PartnerRateServiceImplTest {

    @InjectMocks
    private PartnerRateServiceImpl partnerRateService;

    @Test
    void testFetchRateForPair_AllValidPairs() {
        // testing every switch statement.

        assertEquals(83.50, partnerRateService.fetchRateForPair("USD/INR"));
        assertEquals(0.012, partnerRateService.fetchRateForPair("INR/USD"));
        assertEquals(0.92, partnerRateService.fetchRateForPair("USD/EUR"));
        assertEquals(1.09, partnerRateService.fetchRateForPair("EUR/USD"));
        assertEquals(1.25, partnerRateService.fetchRateForPair("GBP/USD"));
    }

    @Test
    void testFetchRateForPair_InvalidPair_ThrowsException() {
        // Testing switch default fallback branch
        assertThrows(ResponseStatusException.class, () -> partnerRateService.fetchRateForPair("XXX/YYY"));
    }
}