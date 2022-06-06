package com.project.daily.service;

import com.project.daily.domain.User;
import com.project.daily.dto.Request.UserSignUpDto;
import com.project.daily.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    UserService userService;
    UserRepository userRepository;

    @AfterEach //메서드가 끝날때 마다 실행
    public void afterEach(){
        userRepository.deleteAll();
    }

    @Test
    public void register() {
        //given
        UserSignUpDto userSignUpDto = new UserSignUpDto("s21023@gsm.hs.kr", "12345");

        //when
        User register = userService.register(userSignUpDto);

        //then
        Assertions.assertThat(userSignUpDto.getEmail()).isEqualTo("s21023@gsm.hs.kr");
    }
}