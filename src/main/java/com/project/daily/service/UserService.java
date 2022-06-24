package com.project.daily.service;

import com.project.daily.domain.User;
import com.project.daily.dto.Request.UserLoginDto;
import com.project.daily.dto.Request.UserSignUpDto;
import com.project.daily.dto.Response.UserLoginResponseDto;

public interface UserService {

    User register(UserSignUpDto userSignUpDto);
    UserLoginResponseDto login(UserLoginDto userLoginDto);

}
