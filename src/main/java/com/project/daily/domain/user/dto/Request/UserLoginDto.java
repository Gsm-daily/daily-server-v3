package com.project.daily.domain.user.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UserLoginDto {

    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$")
    @NotBlank
    private String Email;

    @Size(min = 4, max = 15, message = "비밀번호는 최소 4자리 최대 15자리 입니다.")
    @NotBlank
    private String password;
}
