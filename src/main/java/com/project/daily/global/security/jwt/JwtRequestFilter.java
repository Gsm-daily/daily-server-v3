package com.project.daily.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("RefreshToken");

        if(accessToken != null && refreshToken != null && tokenProvider.getTokenType(accessToken).equals("accessToken")) {
            if(tokenProvider.isExpired(accessToken) && tokenProvider.getTokenType(refreshToken).equals("refreshToken") && !tokenProvider.isExpired(refreshToken)) {

            }
        }
    }
}
