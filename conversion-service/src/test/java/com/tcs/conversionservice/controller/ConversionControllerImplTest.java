package com.tcs.conversionservice.controller;

import com.tcs.conversionservice.service.FxConversionService;
import com.tcs.fxcommon.dto.ConversionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConversionControllerImpl.class)
class ConversionControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FxConversionService fxConversionService;

    @Test
    void testConvert_Returns200Ok() throws Exception {

        ConversionResponse mockResponse = new ConversionResponse("USD", "INR", 100, 84.0, 8400.0, false);
        when(fxConversionService.convert("USD", "INR", 100)).thenReturn(mockResponse);


        mockMvc.perform(get("/convert")
                        .param("source", "USD")
                        .param("target", "INR")
                        .param("amount", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source").value("USD"))
                .andExpect(jsonPath("$.convertedAmount").value(8400.0));
    }

    @Test
    void testConvert_NegativeAmount_Returns400BadRequest() throws Exception {

        mockMvc.perform(get("/convert")
                        .param("source", "USD")
                        .param("target", "INR")
                        .param("amount", "-50"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("Amount must be greater than zero")));
    }
}