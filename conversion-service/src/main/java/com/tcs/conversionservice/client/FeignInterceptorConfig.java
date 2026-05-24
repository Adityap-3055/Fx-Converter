package com.tcs.conversionservice.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Grab the ID from the backpack
                String correlationId = MDC.get("correlationId");
                if (correlationId != null) {
                    // Inject it into the Feign request heading to the Partner Service
                    template.header("X-Correlation-ID", correlationId);
                }
            }
        };
    }
}