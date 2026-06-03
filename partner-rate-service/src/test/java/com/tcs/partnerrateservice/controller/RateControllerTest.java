package com.tcs.partnerrateservice.controller;

import com.tcs.partnerrateservice.service.PartnerRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RateControllerImpl.class)
class RateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerRateService partnerRateService;

    @Test
    void testGetRateEndpoint_Returns200Ok() throws Exception {

        when(partnerRateService.fetchRateForPair("USD/INR")).thenReturn(83.5);

        // HTTP simulation type
        mockMvc.perform(get("/api/partner/v1/rates")
                        .param("source", "USD")
                        .param("target", "INR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(83.5))
                .andExpect(jsonPath("$.pair").value("USD/INR"));
    }
}