package com.project.daily.global.exeception.exceptions;

import com.project.daily.global.exeception.ErrorCode;
import lombok.Getter;

@Getter
public class RefreshTokenExpirationException extends RuntimeException {

    private ErrorCode errorCode;

    public RefreshTokenExpirationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
