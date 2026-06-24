package com.luv4code.ums.controller;

import com.luv4code.ums.dto.AuthResponse;
import com.luv4code.ums.dto.LoginRequest;
import com.luv4code.ums.dto.RegisterRequest;
import com.luv4code.ums.entity.User;
import com.luv4code.ums.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User registered successfully with username: " + user.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
