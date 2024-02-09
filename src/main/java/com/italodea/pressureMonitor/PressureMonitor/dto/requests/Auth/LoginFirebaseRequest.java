package com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginFirebaseRequest {
    private String name;
    private String email;
    private String firebaseToken;
}
