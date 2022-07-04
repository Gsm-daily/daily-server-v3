package com.project.daily.domain.user.service;

import com.project.daily.domain.user.dto.Request.RefreshTokenDto;

import java.util.Map;

public interface RefreshTokenService {
    Map<String, String> refreshToken(String refreshToken, RefreshTokenDto refreshTokenDto);
}
