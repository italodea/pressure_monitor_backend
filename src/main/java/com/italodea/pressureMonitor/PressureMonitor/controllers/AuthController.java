package com.italodea.pressureMonitor.PressureMonitor.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.LoginFirebaseRequest;
import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.LoginRequest;
import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.RegisterRequest;
import com.italodea.pressureMonitor.PressureMonitor.dto.responses.JwtAuthResponse;
import com.italodea.pressureMonitor.PressureMonitor.services.Auth.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest data) {
        String token = authService.login(data);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterRequest data) {
        String token = authService.register(data);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/login-firebase")
    public ResponseEntity<JwtAuthResponse> registerFirebase(@RequestBody LoginFirebaseRequest data) {
        String token = authService.loginFirebase(data);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

}