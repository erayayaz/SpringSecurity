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

    public void saveUserToken(String token, User user) {
        Token newToken = Token.builder()
                .token(token)
                .loggedOut(false)
                .user(user)
                .build();

        repository.save(newToken);
    }

    public List<Token> findAllTokenByUser(Integer userId) {
        return repository.findAllTokenByUser(userId);
    }

    public Optional<Token> findTokenByToken(String token) {
        return repository.findByToken(token);
    }

    public void saveToken(Token token) {
        repository.save(token);
    }
}
