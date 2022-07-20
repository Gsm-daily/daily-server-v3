package com.project.daily.global.exeception.exceptions;

import com.project.daily.global.exeception.ErrorCode;
import lombok.Getter;

@Getter
public class UnknownServerException extends RuntimeException {

    private ErrorCode errorCode;

    public UnknownServerException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
