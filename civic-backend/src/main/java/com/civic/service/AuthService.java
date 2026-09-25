package com.civic.service;

import com.civic.dto.*;
import com.civic.entity.User;
import com.civic.entity.Officer;
import com.civic.enums.Role;
import com.civic.repository.UserRepository;
import com.civic.repository.OfficerRepository;
import com.civic.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OfficerRepository officerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, OfficerRepository officerRepository,
                        PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.officerRepository = officerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.CITIZEN);
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        return new AuthResponse(token, user.getRole().name(), user.getName(), user.getId());
    }

    public AuthResponse login(LoginRequest req) {
        var userOpt = userRepository.findByEmail(req.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
            return new AuthResponse(token, user.getRole().name(), user.getName(), user.getId());
        }

        Officer officer = officerRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), officer.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(officer.getEmail(), "OFFICER", officer.getId());
        return new AuthResponse(token, "OFFICER", officer.getName(), officer.getId());
    }
}