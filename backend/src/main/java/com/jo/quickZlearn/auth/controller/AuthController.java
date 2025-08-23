package com.jo.quickZlearn.auth.controller;

import com.jo.quickZlearn.auth.dto.AuthResponse;
import com.jo.quickZlearn.auth.dto.LoginRequest;
import com.jo.quickZlearn.auth.dto.RegisterRequest;
import com.jo.quickZlearn.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        String token= authService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("hello")
    public static String hello(){
        return "hello";
    }

}
