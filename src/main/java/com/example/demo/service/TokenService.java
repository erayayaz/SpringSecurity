package com.example.demo.service;

import com.example.demo.repository.TokenRepository;
import com.example.demo.user.Token;
import com.example.demo.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenService {

    private final TokenRepository repository;

    public TokenService(TokenRepository repository) {
        this.repository = repository;
    }

    public void saveUserToken(String accessToken, String refreshToken, User user) {
        Token newToken = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .loggedOut(false)
                .user(user)
                .build();

        repository.save(newToken);
    }

    public List<Token> findAllTokenByUser(Integer userId) {
        return repository.findAllTokenByUser(userId);
    }

    public Optional<Token> findByAccessToken(String accessToken) {
        return repository.findByAccessToken(accessToken);
    }

    public void saveToken(Token token) {
        repository.save(token);
    }

    public Optional<Token> findByRefreshToken(String refreshToken) {
        return repository.findByRefreshToken(refreshToken);
    }
}
