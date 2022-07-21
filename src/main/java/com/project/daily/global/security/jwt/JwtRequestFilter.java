package com.project.daily.global.security.jwt;

import com.project.daily.global.exeception.exceptions.TokenInvalidException;
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

import static com.project.daily.global.exeception.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    /*
    토큰 없을때
    accessToken 만료됐을때
    토큰 형식이 accessToken이 아닐때
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader("Authorization");

        if(accessToken != null) {
            if (!tokenProvider.isExpired(accessToken)) {
                System.out.println("sldkfjaskjf");
                String userEmail = accessTokenExractEmail(accessToken);
                if (userEmail != null) registerUserInfoInSecurityContext(userEmail, request);
                System.out.println("여기까지 옴");
            }
            if(!tokenProvider.getTokenType(accessToken).equals("accessToken")){
                throw new TokenInvalidException("invalid token", TOKEN_INVALID); // 단일 책임 원칙 때문에 CustomException으로 다 몰빵하는건 바꿔야함
            }
        }

        filterChain.doFilter(request, response);
    }

    private String accessTokenExractEmail(String accessToken){
        try {
            return tokenProvider.getUserEmail(accessToken);
        } catch (JwtException e) {
            throw new RuntimeException();
        }
    }

    private void registerUserInfoInSecurityContext(String userEmail, HttpServletRequest request) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 현재 요청 기반으로 securityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (NullPointerException e) {
            throw new RuntimeException();
        }
    }
}
