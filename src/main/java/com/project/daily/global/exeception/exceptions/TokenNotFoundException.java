package com.project.daily.global.exeception.exceptions;

import com.project.daily.global.exeception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public TokenNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
