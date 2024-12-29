package com.example.travel_planner_springboot_rest_api.controller;

import com.example.travel_planner_springboot_rest_api.model.User;
import com.example.travel_planner_springboot_rest_api.service.AuthService;
import com.example.travel_planner_springboot_rest_api.util.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Inject JwtTokenUtil

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        return ResponseEntity.ok(authService.login(user.getEmail(), user.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        String username = jwtTokenUtil.extractUsername(refreshToken);
        if (jwtTokenUtil.validateToken(refreshToken, username)) {
            String accessToken = jwtTokenUtil.generateAccessToken(username);

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken); // Optionally refresh this too
            response.put("expiresIn", 3600); // 1 hour

            return ResponseEntity.ok(response);
        }

        throw new RuntimeException("Invalid refresh token");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        authService.logout(refreshToken);
        return ResponseEntity.ok("Logged out successfully");
    }
}
