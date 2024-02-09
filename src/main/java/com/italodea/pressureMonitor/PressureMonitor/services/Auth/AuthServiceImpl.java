package com.italodea.pressureMonitor.PressureMonitor.services.Auth;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.italodea.pressureMonitor.PressureMonitor.auth.JwtTokenProvider;
import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.LoginFirebaseRequest;
import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.LoginRequest;
import com.italodea.pressureMonitor.PressureMonitor.dto.requests.Auth.RegisterRequest;
import com.italodea.pressureMonitor.PressureMonitor.entities.User;
import com.italodea.pressureMonitor.PressureMonitor.repositories.UserRepository;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String login(LoginRequest data) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                data.getEmail(),
                data.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterRequest data) {
        User user = new User();
        user.setName(data.getName());
        user.setEmail(data.getEmail());

        String hashedPassword = passwordEncoder.encode(data.getPassword());
        user.setPassword(hashedPassword);

        if (data.getGoogleId() != null) {
            user.setGoogleId(data.getGoogleId());
        }

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                data.getEmail(),
                data.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;

    }

    @Override
    public String loginFirebase(LoginFirebaseRequest data) {
        User user = userRepository.findOneByEmail(data.getEmail());
        if (user == null) {
            user = new User();
            user.setEmail(data.getEmail());
            user.setName(data.getName());
            user.setGoogleId(data.getFirebaseToken());
        } else {
            user.setGoogleId(data.getFirebaseToken());
        }

        String hashedPassword = passwordEncoder.encode(data.getFirebaseToken());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                data.getEmail(),
                data.getFirebaseToken()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}