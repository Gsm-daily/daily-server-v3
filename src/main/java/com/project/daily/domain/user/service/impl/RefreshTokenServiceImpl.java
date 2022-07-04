package com.project.daily.domain.user.service.impl;

import com.project.daily.domain.user.User;
import com.project.daily.domain.user.dto.Request.RefreshTokenDto;
import com.project.daily.domain.user.exeception.CustomException;
import com.project.daily.domain.user.repository.UserRepository;
import com.project.daily.domain.user.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.project.daily.domain.user.exeception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRepository userRepository;

    @Override
    public Map<String, String> refreshToken(String refreshToken, RefreshTokenDto refreshTokenDto) {

        String email = refreshTokenDto.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if(userRepository)

        Map<String, String> map = new HashMap<>();

    }
}
