package com.project.daily.global.exeception.exceptions;

import com.project.daily.global.exeception.ErrorCode;
import lombok.Getter;

@Getter
public class KeyNotCorrectException extends RuntimeException{

    private ErrorCode errorCode;

    public KeyNotCorrectException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
