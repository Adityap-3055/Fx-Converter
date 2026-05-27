package com.tcs.conversionservice.filter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String MDC_KEY = "correlationId";

    /**
     * Processes incoming requests to ensure correlation ID availability.
     *
     * @param request the HTTP servlet request containing potential correlation ID header
     * @param response the HTTP servlet response
     * @param chain the filter chain for request continuation
     * @throws IOException if an I/O error occurs during filter processing
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);

        // If a request hits this service directly, generate an ID just in case
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Put it in the MDC backpack!
        MDC.put(MDC_KEY, correlationId);

        try {
            chain.doFilter(request, response);
        } finally {
            // ALWAYS clean up the backpack to prevent memory leaks across threads
            MDC.remove(MDC_KEY);
        }
    }
}