package com.tcs.conversionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "partner-rate-service", url = "http://localhost:8082")
public interface PartnerRateClient {

    @GetMapping("/api/partner/v1/rates")
    RateResponse getRate(@RequestParam("source") String source, @RequestParam("target") String target);
}
