package com.project.daily.domain.user.exeception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
}
