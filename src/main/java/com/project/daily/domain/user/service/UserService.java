package com.project.daily.domain.user.service;

import com.project.daily.domain.user.dto.Request.EmailDto;
import com.project.daily.domain.user.dto.Request.UserLoginDto;
import com.project.daily.domain.user.dto.Request.UserSignUpDto;
import com.project.daily.domain.user.dto.Response.UserLoginResponseDto;

public interface UserService {

    Long register(UserSignUpDto userSignUpDto);
    void sendEmail(EmailDto emailDto);
    UserLoginResponseDto login(UserLoginDto userLoginDto);

}
