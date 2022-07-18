package com.project.daily.domain.sign.service;

import com.project.daily.domain.sign.dto.Request.RefreshTokenDto;

import java.util.Map;

public interface RefreshTokenService {
    Map<String, String> refreshToken(String refreshToken, RefreshTokenDto refreshTokenDto);
}
