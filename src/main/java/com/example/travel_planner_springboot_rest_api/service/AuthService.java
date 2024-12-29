package com.example.travel_planner_springboot_rest_api.service;

import com.example.travel_planner_springboot_rest_api.model.User;
import com.example.travel_planner_springboot_rest_api.repository.UserRepository;
import com.example.travel_planner_springboot_rest_api.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public String login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return jwtTokenUtil.generateToken(userOpt.get().getEmail());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
