package com.project.daily.domain.sign.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponseDto {

    private final String accessToken;
    private final String refreshToken;
}
