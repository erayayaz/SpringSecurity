package com.example.demo.config;

import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;
import com.example.demo.user.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenService tokenService;

    public CustomLogoutHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        final String token = authHeader.substring(7);

        // get stored token from database
        Token storedToken = tokenService.findTokenByToken(token).orElse(null);

        // invalidate the token i.e. make logout true
        if (storedToken != null) {
            storedToken.setLoggedOut(true);
            tokenService.saveToken(storedToken);
        }
    }
}
