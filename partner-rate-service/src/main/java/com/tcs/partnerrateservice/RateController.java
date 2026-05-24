package com.tcs.partnerrateservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partner/v1")
public class RateController {

    private static final Logger log = LoggerFactory.getLogger(RateController.class);

    @GetMapping("/rates")
    public ResponseEntity<RateResponse> getExchangeRate(
            @RequestParam String source,
            @RequestParam String target)
    {

        String pair = source.toUpperCase() + "/" + target.toUpperCase();
        log.info("Partner API received rate request for pair: {}", pair);

        double rate = switch (pair){

            case "USD/INR" -> 95.00;
            case "INR/USD" -> 0.010;
            case "EUR/INR" -> 111.00;
            case "INR/EUR" -> 0.009;
            case "USD/EUR" -> 0.86;
            case "EUR/USD" -> 1.16;
            case "GBP/USD" -> 1.34;
            default -> 1;

        };

        return ResponseEntity.ok(new RateResponse(pair, rate));
    }
}
