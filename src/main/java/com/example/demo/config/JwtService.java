package com.example.demo.config;

import com.example.demo.service.TokenService;
import com.example.demo.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.accessTokenExpiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refreshTokenExpiration}")
    private long refreshTokenExpire;

    private final TokenService tokenService;

    public JwtService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpire); //
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpire);
    }

    public String generateToken(User user, long expiresIn) {
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiresIn))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValidAccessToken(String accessToken, UserDetails userDetails) {
        String username = extractUsername(accessToken);

        boolean isValidToken = tokenService.findByAccessToken(accessToken).map(t -> !t.isLoggedOut())
                .orElse(false);

        return username.equals(userDetails.getUsername()) && isTokenExpired(accessToken) && isValidToken;
    }

    public boolean isValidRefreshToken(String refreshToken, User user) {
        String username = extractUsername(refreshToken);

        boolean isValidRefreshToken = tokenService.findByRefreshToken(refreshToken).map(t -> !t.isLoggedOut())
                .orElse(false);

        return username.equals(user.getUsername()) && isTokenExpired(refreshToken) && isValidRefreshToken;
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
