package com.example.TranslationManagementSystem.controller;

import com.example.TranslationManagementSystem.dto.AuthRequest;
import com.example.TranslationManagementSystem.entity.UserEntity;
import com.example.TranslationManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        String token = userService.authenticate(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }
}

