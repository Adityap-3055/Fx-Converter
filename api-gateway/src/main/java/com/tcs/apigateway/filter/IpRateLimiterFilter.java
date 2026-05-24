package com.tcs.apigateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.fxcommon.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IpRateLimiterFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(IpRateLimiterFilter.class);

    // to track the usage history of each IP address
    private final Map<String,TokenBucket> buckets = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){

        // get the user's ip address
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();

        // fetch or create a bucket for this ip
        TokenBucket bucket = buckets.computeIfAbsent(ip, k -> new TokenBucket());

        // check if the request is allowed as per the logic (max 3 calls per 10 secs)
        if(bucket.tryConsume()){
            log.info("Request allowed for IP: {}", ip);
            return chain.filter(exchange);
        } else {
            log.warn("Rate limit exceeded for IP: {}. Rejecting request.",ip);
            return rejectRequest(exchange);
        }
    }

    private Mono<Void> rejectRequest(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorObj = new ErrorResponse(
                429,
                "Too Many Requests",
                "You have exceeded your limit of 3 requests per 10 seconds. Try after sometime.",
                exchange.getRequest().getPath().value()
        );

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorObj);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            return response.setComplete();
        }

    }

    @Override
    public int getOrder() {
        // Run this filter before routing
        return -100;
    }



    // --- Inner class representing the Rate Limiting algorithm ---
    private static class TokenBucket {
        private int tokens = 3; // Max 3 requests
        private Instant lastRefillTime = Instant.now();

        public synchronized boolean tryConsume() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        private void refill() {
            Instant now = Instant.now();
            // If 10 seconds have passed since the last refill, reset the bucket
            if (now.isAfter(lastRefillTime.plusSeconds(10))) {
                tokens = 3;
                lastRefillTime = now;
            }
        }
    }
}
