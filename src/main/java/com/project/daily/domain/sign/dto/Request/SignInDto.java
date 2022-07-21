package com.project.daily.domain.sign.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class SignInDto {

    @Email
    @NotBlank
    private String email;

    @Size(min = 4, max = 15)
    @NotBlank
    private String password;
}
