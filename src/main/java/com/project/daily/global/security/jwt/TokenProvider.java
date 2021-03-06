package com.project.daily.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final Long ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 1L; // 만료시간 3시간
    private static final Long REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 5L; // 14 * 24 * 60 * 60 * 1000L

    @RequiredArgsConstructor
    enum TokenType {
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken");

        private final String value;
    }

    @RequiredArgsConstructor
    enum TokenClaimName {
        USER_EMAIL("userEmail"),
        TOKEN_TYPE("tokenType");

        final String value;
    }

    private Key getSigningKey(String secretKey) {
        byte keyByte[] = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException, IllegalArgumentException, UnsupportedOperationException { // 여기서 throws를 해줬기 때문에 상위 계층 한테 간다.
        return Jwts.parserBuilder() // token 추출 할 때 사용
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmail(String token) {
        return extractAllClaims(token).get(TokenClaimName.USER_EMAIL.value, String.class); // .get(ClaimName, Object)
    }

    public String getTokenType(String token) {
        return extractAllClaims(token).get(TokenClaimName.TOKEN_TYPE.value, String.class);
    }

    public Boolean isExpired(String token) {
        try{
            extractAllClaims(token).getExpiration();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private String doGenerateToken(String userEmail, TokenType tokenType, Long expiredTime) {
        final Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);
        claims.put("tokenType", tokenType.value);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(String email) {
        return doGenerateToken(email, TokenType.ACCESS_TOKEN, ACCESS_TOKEN_EXPIRED_TIME);
    }

    public String generateRefreshToken(String email) {
        return doGenerateToken(email, TokenType.REFRESH_TOKEN, REFRESH_TOKEN_EXPIRED_TIME);
    }

    public String getRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("RefreshToken");
        if(refreshToken != null && isExpired(refreshToken)) {
            return refreshToken;
        } else {
            return null;
        }
    }

}
