package com.example.demo.service;

import com.example.demo.config.JwtService;
import com.example.demo.repository.UserRepository;
import com.example.demo.user.AuthenticationResponse;
import com.example.demo.user.Token;
import com.example.demo.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public AuthenticationResponse register(User request) {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        user = userRepository.save(user);

        String token = jwtService.generateToken(user);
        tokenService.saveUserToken(token, user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        String token = jwtService.generateToken(user);

        List<Token> validTokenListByUser = tokenService.findAllTokenByUser(user.getId());
        if (!validTokenListByUser.isEmpty()) {
            validTokenListByUser.forEach(t -> t.setLoggedOut(true));
        }

        tokenService.saveUserToken(token, user);

        return new AuthenticationResponse(token);
    }
}
