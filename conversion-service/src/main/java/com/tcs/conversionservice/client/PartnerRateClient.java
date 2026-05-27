package com.tcs.conversionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * connect to partner rate service to fetch the detials
 */
@FeignClient(name = "partner-rate-service", url = "http://localhost:8082")
public interface PartnerRateClient {

    /**
     *
     * @param source - 3 letter currency annotation to convert money from (e.g: "USD")
     * @param target - 3 letter currency annotation to convert money to (e.g: "INR")
     * @return - the amount result after converting including the fee markup
     */
    @GetMapping("/api/partner/v1/rates")
    RateResponse getRate(@RequestParam("source") String source, @RequestParam("target") String target);
}
