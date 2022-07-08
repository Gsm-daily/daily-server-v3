package com.project.daily.domain.user.controller;

import com.project.daily.domain.user.dto.Request.UserLoginDto;
import com.project.daily.domain.user.dto.Request.UserSignUpDto;
import com.project.daily.domain.user.dto.Response.UserLoginResponseDto;
import com.project.daily.domain.user.service.UserService;
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
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @PostMapping("/register")
    public CommonResultResponse register(@Valid @RequestBody UserSignUpDto userSignUpDto) {
        userService.register(userSignUpDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }
}
