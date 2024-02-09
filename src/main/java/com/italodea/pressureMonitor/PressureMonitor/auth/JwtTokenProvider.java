package com.italodea.pressureMonitor.PressureMonitor.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.italodea.pressureMonitor.PressureMonitor.entities.User;
import com.italodea.pressureMonitor.PressureMonitor.repositories.UserRepository;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt-secret}")
    private String jwtSecret;

    @Value("${jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findOneByEmail(username);
        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("id", user.getId())
                .claim("googleId", user.getGoogleId())
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key() {
        byte[] secretBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public String getUsername(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public User getUser(String token) {
        Long id = Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);

        return userRepository.findById(id).get();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}