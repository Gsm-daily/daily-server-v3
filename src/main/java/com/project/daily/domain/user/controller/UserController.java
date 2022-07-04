package com.project.daily.domain.user.controller;

import com.project.daily.domain.user.User;
import com.project.daily.domain.user.dto.Request.UserLoginDto;
import com.project.daily.domain.user.dto.Request.UserSignUpDto;
import com.project.daily.domain.user.dto.Response.UserLoginResponseDto;
import com.project.daily.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody UserSignUpDto userSignUpDto) {
        return userService.register(userSignUpDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }
}
