package com.project.daily.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDto {

    private String Email;
    private String password;
}
