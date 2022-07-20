package com.project.daily.global.exeception.exceptions;

import com.project.daily.global.exeception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenExpirationException extends RuntimeException {

    private ErrorCode errorCode;

    public TokenExpirationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
