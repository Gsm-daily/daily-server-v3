package com.project.daily.domain.sign.controller;

import com.project.daily.domain.sign.dto.Request.RefreshTokenDto;
import com.project.daily.domain.sign.service.RefreshTokenService;
import com.project.daily.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;

    @PutMapping("/refreshToken")
    public Map<String, String> refreshToken(HttpServletRequest request, @RequestBody RefreshTokenDto refreshTokenDto) {
        return refreshTokenService.refreshToken(tokenProvider.getRefreshToken(request), refreshTokenDto);
    }
}
