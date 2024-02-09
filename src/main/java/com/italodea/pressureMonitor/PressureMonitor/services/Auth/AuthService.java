package com.italodea.pressureMonitor.PressureMonitor.services.Auth;

import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.LoginFirebaseRequest;
import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.LoginRequest;
import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.RegisterRequest;

public interface AuthService {
    String login(LoginRequest loginDto);

    String register(RegisterRequest registerDto);

    String loginFirebase(LoginFirebaseRequest data);
}