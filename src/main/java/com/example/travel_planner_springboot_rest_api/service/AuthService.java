package com.example.travel_planner_springboot_rest_api.service;

import com.example.travel_planner_springboot_rest_api.model.User;
import com.example.travel_planner_springboot_rest_api.repository.UserRepository;
import com.example.travel_planner_springboot_rest_api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public Map<String, Object> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            User user = userOpt.get();
            String accessToken = jwtTokenUtil.generateAccessToken(user.getEmail());
            String refreshToken = jwtTokenUtil.generateRefreshToken(user.getEmail());

            // Save the refresh token in the database
            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            response.put("expiresIn", 3600); // 1 hour

            return response;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public Map<String, Object> refreshToken(String refreshToken) {
        Optional<User> userOpt = userRepository.findByRefreshToken(refreshToken);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Validate if the refresh token matches and is not expired
            if (jwtTokenUtil.validateToken(refreshToken, user.getEmail())) {
                String newAccessToken = jwtTokenUtil.generateAccessToken(user.getEmail());
                String newRefreshToken = jwtTokenUtil.generateRefreshToken(user.getEmail());

                // Update refresh token in the database (token rotation)
                user.setRefreshToken(newRefreshToken);
                userRepository.save(user);

                Map<String, Object> response = new HashMap<>();
                response.put("accessToken", newAccessToken);
                response.put("refreshToken", newRefreshToken);
                response.put("expiresIn", 3600); // 1 hour

                return response;
            }
        }

        throw new RuntimeException("Invalid or expired refresh token");
    }

    public void logout(String refreshToken) {
        Optional<User> userOpt = userRepository.findByRefreshToken(refreshToken);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setRefreshToken(null); // Invalidate the refresh token
            userRepository.save(user);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
