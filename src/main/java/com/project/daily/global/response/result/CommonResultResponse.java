package com.project.daily.global.response.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CommonResultResponse {

    private boolean success;
    private String msg;
    private HttpStatus httpStatus;
}
