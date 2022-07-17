package com.project.daily.domain.user.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UserLoginDto {

    @Email
    @NotBlank
    private String email;

    @Size(min = 4, max = 15, message = "비밀번호는 최소 4자리 최대 15자리 입니다.")
    @NotBlank
    private String password;
}
