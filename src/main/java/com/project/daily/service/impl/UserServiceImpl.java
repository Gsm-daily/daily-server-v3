package com.project.daily.service.impl;

import com.project.daily.domain.User;
import com.project.daily.dto.Request.UserLoginDto;
import com.project.daily.dto.Request.UserSignUpDto;
import com.project.daily.dto.Response.UserLoginResponseDto;
import com.project.daily.exeception.CustomException;
import com.project.daily.repository.UserRepository;
import com.project.daily.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.daily.exeception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User register(UserSignUpDto userSignUpDto) {

        Optional<User> findByEmail = userRepository.findByEmail(userSignUpDto.getEmail());

        if(findByEmail.isEmpty()) {
            throw new CustomException(USED_EMAIL);
        }

        userSignUpDto.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
        User user = userSignUpDto.toEntity();

        return userRepository.save(user);
    }

    @Override
    public UserLoginResponseDto login(UserLoginDto userLoginDto) {

            User user = userRepository.findByEmail(userLoginDto.getEmail())
                    .orElseThrow(()-> new CustomException(USER_NOT_FOUND));

            if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
                throw new CustomException(PASSWORD_NOT_CORRECT);
            }


        }
    }


}
