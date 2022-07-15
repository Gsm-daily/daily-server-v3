package com.project.daily.domain.user.service;

import com.project.daily.domain.user.User;
import com.project.daily.domain.user.dto.Request.UserLoginDto;
import com.project.daily.domain.user.dto.Request.UserSignUpDto;
import com.project.daily.domain.user.dto.Response.UserLoginResponseDto;
import com.project.daily.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void register() {

        //given
        UserSignUpDto userSignUpDto = new UserSignUpDto("s211ddf5@naver.com", "12345h");

        //when
        Long register = userService.register(userSignUpDto);
        System.out.println("register = " + register);

        //then
        assertThat(register).isEqualTo(userRepository.findByEmail(userSignUpDto.getEmail()).orElseThrow().getUser_id());
    }

    @Test
    void login() {

        //given
        UserLoginDto loginDto = new UserLoginDto("s211ddf5@naver.com", "12345h");

        //when
        UserLoginResponseDto login = userService.login(loginDto);

        //then
        assertNotNull(login);
    }
}