package com.tcs.partnerrateservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateResponse {

    private String pair;
    private double rate;
}
