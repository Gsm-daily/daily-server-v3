package com.project.daily.exeception.Handler;

import com.project.daily.exeception.CustomException;
import com.project.daily.exeception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.project.daily.exeception.ErrorCode.USED_EMAIL;

@RestControllerAdvice
@Slf4j
public class ErrorExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataException() { //hibernate 관련 에러를 처리한다.
        log.error("handleDataException throw Exception : {}", USED_EMAIL);
        return ErrorResponse.toResponseEntity(USED_EMAIL);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.error("{}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }


}
