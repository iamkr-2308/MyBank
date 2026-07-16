package com.iamkr23.bankapplication.service;

import com.iamkr23.bankapplication.dto.AuthResponseDTO;
import com.iamkr23.bankapplication.dto.LoginRequestDTO;
import com.iamkr23.bankapplication.dto.RegisterRequestDTO;
import com.iamkr23.bankapplication.entity.User;
import com.iamkr23.bankapplication.repository.UserRepository;
import com.iamkr23.bankapplication.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequestDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        String token = jwtUtil.generateToken(dto.getUsername());
        return new AuthResponseDTO(token, dto.getUsername());
    }
}