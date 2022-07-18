package com.project.daily.domain.user.service;

import com.project.daily.domain.sign.dto.Request.SignInDto;
import com.project.daily.domain.sign.dto.Request.SignUpDto;
import com.project.daily.domain.sign.dto.Response.SignInResponseDto;
import com.project.daily.domain.sign.repository.UserRepository;
import com.project.daily.domain.sign.service.SignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SignService signService;

    @Test
    void register() {

        //given
        SignUpDto signUpDto = new SignUpDto("s211ddf5@naver.com", "12345h");

        //when
        Long register = signService.register(signUpDto);
        System.out.println("register = " + register);

        //then
        assertThat(register).isEqualTo(userRepository.findByEmail(signUpDto.getEmail()).orElseThrow().getUser_id());
    }

    @Test
    void login() {

        //given
        SignInDto loginDto = new SignInDto("s211ddf5@naver.com", "12345h");

        //when
        SignInResponseDto login = signService.login(loginDto);

        //then
        assertNotNull(login);
    }
}