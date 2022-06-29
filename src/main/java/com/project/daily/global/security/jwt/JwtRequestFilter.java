package com.project.daily.global.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
                accessToken = generateNewAccessToken(refreshToken);
                writeResponse(response, accessToken);
            }
            String userEmail = accessTokenExractEmail(accessToken);
            if(userEmail != null)
                resi
        }
    }

    private void writeResponse(HttpServletResponse response, String accessToken) throws IOException {
        String bodyRoJson = getBodyToJson();
        response.addHeader("accessToken", accessToken);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.
    }

    private String getBodyToJson() throws JsonProcessingException {
        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("msg", "token is regenerated");
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        String bodyToJson = objectMapper.writeValueAsString(body);
        return bodyToJson;
    }
}
