package com.project.daily.global.exeception.exceptions;

import com.project.daily.global.exeception.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public UserNotFoundException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
