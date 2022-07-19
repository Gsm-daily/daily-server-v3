package com.project.daily.domain.sign.controller;

import com.project.daily.domain.sign.dto.Request.CheckEmailKeyDto;
import com.project.daily.domain.sign.dto.Request.EmailDto;
import com.project.daily.domain.sign.dto.Request.SignInDto;
import com.project.daily.domain.sign.dto.Request.SignUpDto;
import com.project.daily.domain.sign.dto.Response.SignInResponseDto;
import com.project.daily.domain.sign.service.SignService;
import com.project.daily.global.response.ResponseService;
import com.project.daily.global.response.result.CommonResultResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignController {

    private final SignService signService;
    private final ResponseService responseService;

    // 회원가입
    @PostMapping("/register")
    public CommonResultResponse register(@Valid @RequestBody SignUpDto signUpDto) {
        signService.register(signUpDto);
        return responseService.getSuccessResult();
    }

    // 회원가입 때 인증번호 발송
    @PostMapping("register/email")
    public CommonResultResponse sendEmail(@Valid @RequestBody EmailDto emailDto) {
        signService.sendEmail(emailDto);
        return responseService.getSuccessResult();
    }

    // 인증번호 확인
    @PostMapping("register/email/check")
    public CommonResultResponse checkEmail(@Valid @RequestBody CheckEmailKeyDto checkEmailKeyDto) {
        signService.checkEmailKey(checkEmailKeyDto);
        return responseService.getSuccessResult();
    }

    // 로그인
    @PostMapping("/login")
    public SignInResponseDto login(@Valid @RequestBody SignInDto signInDto) {
        return signService.login(signInDto);
    }

    @GetMapping("/test")
    public String TokenTest() {
        return "TokenTest";
    }
}
