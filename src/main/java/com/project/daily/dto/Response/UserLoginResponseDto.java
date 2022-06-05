package com.project.daily.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {

    private final Long user_id;
    private final String accessToken;
    private final String refreshToken;
}
