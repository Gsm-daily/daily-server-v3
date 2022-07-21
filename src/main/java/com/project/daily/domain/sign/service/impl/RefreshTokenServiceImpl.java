package com.project.daily.domain.sign.service.impl;

import com.project.daily.domain.sign.service.RefreshTokenService;
import com.project.daily.domain.sign.User;
import com.project.daily.domain.sign.dto.Request.RefreshTokenDto;
import com.project.daily.domain.sign.repository.UserRepository;
import com.project.daily.global.exeception.exceptions.RefreshTokenNotFoundException;
import com.project.daily.global.exeception.exceptions.TokenInvalidException;
import com.project.daily.global.exeception.exceptions.UserNotFoundException;
import com.project.daily.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.project.daily.global.exeception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    public Map<String, String> refreshToken(String refreshToken, RefreshTokenDto refreshTokenDto) {

        String email = refreshTokenDto.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found", USER_NOT_FOUND));

        if(user.getRefreshToken() == null) {
            throw new TokenInvalidException("Invalid token", TOKEN_INVALID);
        }
        Map<String, String> map = new HashMap<>();

        if(user.getRefreshToken().equals(refreshToken) && tokenProvider.isExpired(refreshToken)) {
            String newAccessToken = tokenProvider.generateAccessToken(email);
            String newRefreshToken = tokenProvider.generateRefreshToken(email);

            user.updateRefreshToken(newRefreshToken);
            map.put("email : ", email);
            map.put("NewAccessToken : ", newAccessToken);
            map.put("NewRefreshToken : ", newRefreshToken);

            return map;
        }
        throw new RefreshTokenNotFoundException("refreshToken not found", REFRESH_TOKEN_NOT_FOUND);
    }
}
