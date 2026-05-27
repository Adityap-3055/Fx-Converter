package com.tcs.conversionservice.controller;

import com.tcs.conversionservice.service.FxConversionService;
import com.tcs.fxcommon.dto.ConversionResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Rest controller for foreign exchange conversions
 */
@RestController
public class ConversionControllerImpl implements ConversionController{

    private  final FxConversionService fxConversionService;


    public ConversionControllerImpl(FxConversionService fxConversionService){
        this.fxConversionService = fxConversionService;
    }


    /**
     *
     * @param source - 3 letter currency annotation to convert money from (e.g: "USD")
     * @param target - 3 letter currency annotation to convert money to (e.g: "INR")
     * @param amount - the amount result after converting including the fee markup
     * @return ConversionResponse that has the applied rate and the final calculated amount.
     */
    @Override
    public ResponseEntity<ConversionResponse> convert(@RequestParam String source,
                                                      @RequestParam String target,
                                                      @RequestParam double amount){

        ConversionResponse response = fxConversionService.convert(
                source.toUpperCase(),
                target.toUpperCase(),
                amount);

        return ResponseEntity.ok(response);
    }
}
