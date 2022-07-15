package com.project.daily.domain.user.service;

import com.project.daily.domain.user.User;
import com.project.daily.domain.user.dto.Request.UserLoginDto;
import com.project.daily.domain.user.dto.Request.UserSignUpDto;
import com.project.daily.domain.user.dto.Response.UserLoginResponseDto;

public interface UserService {

    Long register(UserSignUpDto userSignUpDto);
    UserLoginResponseDto login(UserLoginDto userLoginDto);

}
