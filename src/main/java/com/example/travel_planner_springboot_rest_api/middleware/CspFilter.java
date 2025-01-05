package com.example.travel_planner_springboot_rest_api.middleware;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CspFilter implements Filter {

    @Value("${app.environment}")
    private String environment;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String nonce = UUID.randomUUID().toString(); // Generate a unique nonce for each request

        if ("development".equals(environment)) {
            // Development CSP (with nonce)
            httpResponse.setHeader("Content-Security-Policy",
                    "script-src 'self' 'unsafe-eval' 'nonce-" + nonce + "';");
        } else {
            // Production CSP (strict, without unsafe)
            httpResponse.setHeader("Content-Security-Policy",
                    "script-src 'self' 'nonce-" + nonce + "';");
        }

        // Pass the nonce to frontend via a custom header
        httpResponse.setHeader("X-CSP-Nonce", nonce);

        chain.doFilter(request, response);
    }
}