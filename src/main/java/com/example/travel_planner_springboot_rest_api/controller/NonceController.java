package com.example.travel_planner_springboot_rest_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class NonceController {
    @GetMapping("/api/nonce")
    public ResponseEntity<String> getNonce() {
        String nonce = UUID.randomUUID().toString();
        return ResponseEntity.ok()
                .header("X-CSP-Nonce", nonce)
                .body("{\"nonce\": \"" + nonce + "\"}");
    }
}
