package com.tcs.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Global reactive filter that serves as the entry point for distributed tracing.
 * Generates a unique UUID (Correlation ID) for every incoming request and injects it
 * into the HTTP headers to trace the request lifecycle across all downstream microservices.
 */
@Component
public class CorrelationTrackingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(CorrelationTrackingFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Generate a unique Trace ID
        String correlationId = UUID.randomUUID().toString();
        log.info("GATEWAY: Generated Correlation ID: {}", correlationId);

        // Mutate the request to append the header so the next microservice can see it
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-Correlation-ID", correlationId)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    @Override
    public int getOrder() {
        return -50;
    }
}