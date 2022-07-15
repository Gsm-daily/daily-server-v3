package com.project.daily.domain.user.dto.Request;

import com.project.daily.domain.user.User;
import com.project.daily.domain.user.enumType.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpDto {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 15, message = "비밀번호는 4자리 초과, 15자리 미만 여야 합니다.")
    private String password;

    public User toEntity(String password){
        return User.builder()
                .email(email)
                .password(password)
                .refreshToken(null)
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();
    }
}
