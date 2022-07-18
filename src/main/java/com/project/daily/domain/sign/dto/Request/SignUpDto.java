package com.project.daily.domain.sign.dto.Request;

import com.project.daily.domain.sign.User;
import com.project.daily.domain.sign.enumType.Role;
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
public class SignUpDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
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
