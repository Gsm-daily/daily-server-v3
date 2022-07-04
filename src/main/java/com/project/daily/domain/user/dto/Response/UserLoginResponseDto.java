package com.project.daily.domain.user.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {

    private final String accessToken;
    private final String refreshToken;
}
