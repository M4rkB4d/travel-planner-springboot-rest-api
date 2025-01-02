package com.example.travel_planner_springboot_rest_api.middleware;

import com.example.travel_planner_springboot_rest_api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Skip JWT processing for login and registration endpoints
        String requestPath = request.getRequestURI();
        if (requestPath.startsWith("/api/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Authorization header or invalid format.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or invalid format");
            return;
        }

        String token = authHeader.substring(7);
        String username = null;

        try {
            username = jwtTokenUtil.extractUsername(token);
        } catch (Exception e) {
            System.out.println("Invalid JWT Token: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.validateToken(token, username)) {
                // Check token expiration
                if (jwtTokenUtil.isAccessToken(token)) { // Ensure it's an access token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication set for user: " + username);
                    System.out.println("Authentication in SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());
                } else {
                    System.out.println("Refresh token used in protected endpoint!");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token type for this endpoint");
                    return;
                }
            } else {
                System.out.println("Token validation failed!");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token validation failed");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
