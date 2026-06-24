package com.luv4code.ums.service;

import com.luv4code.ums.dto.AuthResponse;
import com.luv4code.ums.dto.LoginRequest;
import com.luv4code.ums.dto.RegisterRequest;
import com.luv4code.ums.dto.UserResponse;
import com.luv4code.ums.entity.User;
import com.luv4code.ums.repository.UserRepository;
import com.luv4code.ums.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User registerUser(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();
        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));
        String token = jwtService.generateToken(request.getEmail());
        return new AuthResponse(token);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("User not found!!");
        });
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(user ->
                UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build())
                .toList();
    }

}
