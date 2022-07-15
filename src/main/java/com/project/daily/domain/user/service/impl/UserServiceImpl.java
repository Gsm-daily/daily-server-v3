package com.project.daily.domain.user.service.impl;

import com.project.daily.domain.user.User;
import com.project.daily.domain.user.dto.Request.UserLoginDto;
import com.project.daily.domain.user.dto.Request.UserSignUpDto;
import com.project.daily.domain.user.dto.Response.UserLoginResponseDto;
import com.project.daily.domain.user.repository.UserRepository;
import com.project.daily.domain.user.service.UserService;
import com.project.daily.global.exeception.CustomException;
import com.project.daily.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.daily.global.exeception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long register(UserSignUpDto userSignUpDto) {
        Optional<User> findByEmail = userRepository.findByEmail(userSignUpDto.getEmail());

        if(findByEmail.isPresent()) {
            throw new CustomException(USED_EMAIL);
        }

        User user = userSignUpDto.toEntity(passwordEncoder.encode(userSignUpDto.getPassword()));
        return userRepository.save(user).getUser_id();
    }

    @Transactional
    @Override
    public UserLoginResponseDto login(UserLoginDto userLoginDto) {

        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(()-> new CustomException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomException(PASSWORD_NOT_CORRECT);
            }

        final String AccessToken = tokenProvider.generateAccessToken(user.getEmail());
        final String RefreshToken = tokenProvider.generateRefreshToken(user.getEmail());

        user.updateRefreshToken(RefreshToken);

        return UserLoginResponseDto.builder()
                .accessToken(AccessToken)
                .refreshToken(RefreshToken)
                .build();
    }
}
