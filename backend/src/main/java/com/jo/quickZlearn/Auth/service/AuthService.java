package com.jo.quickZlearn.Auth.service;

import com.jo.quickZlearn.Auth.config.JwtUtil;
import com.jo.quickZlearn.Auth.dto.LoginRequest;
import com.jo.quickZlearn.Auth.dto.RegisterRequest;
import com.jo.quickZlearn.Auth.entity.Users;
import com.jo.quickZlearn.Auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already in use");
        }

        Users users = new Users();
        users.setUsername(request.getUsername());
        users.setEmail(request.getEmail());
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(users);
    }

    public String login(LoginRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(userDetails);
    }

}
