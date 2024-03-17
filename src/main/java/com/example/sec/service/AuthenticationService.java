package com.example.sec.service;

import com.example.sec.dto.authentication.AuthenticationRequest;
import com.example.sec.dto.authentication.AuthenticationResponse;
import com.example.sec.dto.register.RegisterRequest;
import com.example.sec.model.Role;
import com.example.sec.model.User;
import com.example.sec.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null) {
            var userDetails = this.userRepository.findByEmail(userEmail).orElseThrow();
            if(jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);

                AuthenticationResponse authenticationResponse =
                        new AuthenticationResponse(accessToken, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }
        }
    }
}
