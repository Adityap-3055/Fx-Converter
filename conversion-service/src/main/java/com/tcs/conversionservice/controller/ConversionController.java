package com.tcs.conversionservice.controller;

import com.tcs.conversionservice.service.FxConversionService;
import com.tcs.fxcommon.dto.ConversionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class ConversionController {

    private  final FxConversionService fxConversionService;

    public ConversionController(FxConversionService fxConversionService){
        this.fxConversionService = fxConversionService;
    }


    @GetMapping
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
