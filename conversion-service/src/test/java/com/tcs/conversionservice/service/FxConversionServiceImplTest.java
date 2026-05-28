package com.tcs.conversionservice.service;

import com.tcs.conversionservice.client.PartnerRateClient;
import com.tcs.conversionservice.client.RateResponse;
import com.tcs.conversionservice.exception.CurrencyNotSupportedException;
import com.tcs.fxcommon.dto.ConversionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FxConversionServiceImplTest {

    @Mock
    private PartnerRateClient partnerRateClient;

    @InjectMocks
    private FxConversionServiceImpl fxConversionService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(fxConversionService, "feeMarkup", 0.05);
    }

    @Test
    void testConvert_Success() {

        RateResponse mockResponse = new RateResponse("USD/INR", 80.0);
        when(partnerRateClient.getRate("USD", "INR")).thenReturn(mockResponse);


        ConversionResponse response = fxConversionService.convert("USD", "INR", 100);


        assertNotNull(response);
        assertEquals(84.0, response.getExchangeRate());
        assertEquals(8400.0, response.getConvertedAmount());
        assertFalse(response.isStaleDate());

        // need to verify the mock was called exactly one time
        verify(partnerRateClient, times(1)).getRate("USD", "INR");
    }

    @Test
    void testPartnerRateFallback() {

        Throwable simulatedError = new RuntimeException("Partner is down");


        ConversionResponse response = fxConversionService.partnerRateFallback("USD", "INR", 100, simulatedError);

        assertNotNull(response);
        assertTrue(response.isStaleDate()); // Stale data flag MUST be true
        assertEquals(0.945, response.getExchangeRate(), 0.001);
        assertEquals(94.5, response.getConvertedAmount(), 0.001);

        // calculation will be : 0.90 backup rate + 5% markup = 0.945. 100 * 0.945 = 94.5
    }

    @Test
    void testPartnerRateFallback_IgnoresCustomException() {

        Throwable validationError = new CurrencyNotSupportedException("Invalid Pair");

        assertThrows(CurrencyNotSupportedException.class, () -> {
            fxConversionService.partnerRateFallback("USD", "XXX", 100, validationError);
        });
    }
}