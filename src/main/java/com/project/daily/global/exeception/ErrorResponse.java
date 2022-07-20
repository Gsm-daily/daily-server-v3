package com.project.daily.global.exeception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private String message;
    private int status;

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMsg();
        this.status = errorCode.getStatus();
    }

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
