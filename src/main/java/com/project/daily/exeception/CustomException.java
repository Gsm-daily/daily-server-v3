package com.project.daily.exeception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
}
