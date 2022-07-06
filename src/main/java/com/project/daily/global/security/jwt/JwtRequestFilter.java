package com.project.daily.global.security.jwt;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader("Authorization");

        if(accessToken != null && !tokenProvider.isExpired(accessToken)) {
            String userEmail = accessTokenExractEmail(accessToken);
            if(userEmail != null)
                registerUserInfoInSecurityContext(userEmail, request);
        }
        filterChain.doFilter(request, response);
    }

    private String accessTokenExractEmail(String accessToken){
        try {
            return tokenProvider.getUserEmail(accessToken);
        } catch (JwtException | IllegalStateException e) {
            throw new RuntimeException();
        }
    }

    private void registerUserInfoInSecurityContext(String userEmail, HttpServletRequest request) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (NullPointerException e) {
            throw new RuntimeException();
        }
    }
}
