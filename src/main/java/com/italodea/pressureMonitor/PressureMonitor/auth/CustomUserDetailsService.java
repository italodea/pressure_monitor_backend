package com.italodea.pressureMonitor.PressureMonitor.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.italodea.pressureMonitor.PressureMonitor.entities.User;
import com.italodea.pressureMonitor.PressureMonitor.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

                User user = userRepository.findOneByEmail(email);
                List<String> roles = new ArrayList<>();
                roles.add("USER");
                UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                                .username(user.getEmail())
                                .password(user.getPassword())
                                .roles(roles.toArray(new String[0]))
                                .build();

                return userDetails;
        }
}