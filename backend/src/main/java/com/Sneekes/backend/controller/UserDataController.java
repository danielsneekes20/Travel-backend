package com.Sneekes.backend.controller;

import com.Sneekes.backend.entity.User;
import com.Sneekes.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.Sneekes.backend.dto.UserDto;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-data")
public class UserDataController {

    private final UserRepository userRepository;

    @Autowired
    public UserDataController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserData(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok(userDto);
    }
}
