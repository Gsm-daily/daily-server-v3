package com.project.daily.domain.sign.service;

import com.project.daily.domain.sign.dto.Request.CheckEmailKeyDto;
import com.project.daily.domain.sign.dto.Request.EmailDto;
import com.project.daily.domain.sign.dto.Request.SignInDto;
import com.project.daily.domain.sign.dto.Request.SignUpDto;
import com.project.daily.domain.sign.dto.Response.SignInResponseDto;

public interface SignService {

    Long register(SignUpDto signUpDto);
    void sendEmail(EmailDto emailDto);
    void checkEmailKey(CheckEmailKeyDto checkEmailKeyDto);
    SignInResponseDto login(SignInDto signInDto);

}
