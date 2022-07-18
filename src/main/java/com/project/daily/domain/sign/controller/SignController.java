package com.project.daily.domain.sign.controller;

import com.project.daily.domain.sign.dto.Request.EmailDto;
import com.project.daily.domain.sign.dto.Request.SignInDto;
import com.project.daily.domain.sign.dto.Request.SignUpDto;
import com.project.daily.domain.sign.dto.Response.SignInResponseDto;
import com.project.daily.domain.sign.service.SignService;
import com.project.daily.global.response.ResponseService;
import com.project.daily.global.response.result.CommonResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignController {

    private final SignService signService;
    private final ResponseService responseService;

    @PostMapping("/register")
    public CommonResultResponse register(@Valid @RequestBody SignUpDto signUpDto) {
        signService.register(signUpDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("register/email")
    public CommonResultResponse sendEmail(@Valid @RequestBody EmailDto emailDto) {
        signService.sendEmail(emailDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public SignInResponseDto login(@Valid @RequestBody SignInDto signInDto) {
        return signService.login(signInDto);
    }
}
