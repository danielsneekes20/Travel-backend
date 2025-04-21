package com.Sneekes.backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.Sneekes.backend.repository.UserRepository;
import com.Sneekes.backend.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.Sneekes.backend.service.JwtUtil;

@RestController
public class HelloController {
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public String home() {
        return "👋 Hello from the backend!";
    }

    @GetMapping("/test")
    public String test() {
    return "Test successful!";
}

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{id:[0-9]+}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElse(null); // of throwen als je dat wilt
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUserData(HttpServletRequest request) {

        // Retrieve JWT token from cookies
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token missing");
        }

        // Validate the JWT token
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // Extract the username from the token
        String username = jwtUtil.extractUsername(token);

        // Fetch the user data
        Optional<User> user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(user);
    }
}