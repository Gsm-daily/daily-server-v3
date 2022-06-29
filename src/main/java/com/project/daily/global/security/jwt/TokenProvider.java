package com.project.daily.global.security.jwt;

import com.project.daily.domain.user.exeception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static com.project.daily.domain.user.exeception.ErrorCode.REFRESH_TOKEN_EXPIRATION;


@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final Long ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 3L; // 만료시간 3시간
    private static final Long REFRESH_TOKEN_EXPIRED_TIME = 14 * 24 * 60 * 60 * 1000L;

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

    public Claims extractAllClaims(String token) throws ExpiredJwtException, IllegalArgumentException, UnsupportedOperationException {
        return Jwts.parserBuilder() // token 추출 할 때 사용
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmail(String token) {

        if(isExpired(token)) {
            throw new CustomException(REFRESH_TOKEN_EXPIRATION);
        }

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
        claims.put("tokenType", tokenType);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()))
                .setIssuedAt(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(String email) {
        return doGenerateToken(email, TokenType.ACCESS_TOKEN, ACCESS_TOKEN_EXPIRED_TIME);
    }

    public String generateRefreshToken(String email) {
        return doGenerateToken(email, TokenType.REFRESH_TOKEN, REFRESH_TOKEN_EXPIRED_TIME);
    }

}
