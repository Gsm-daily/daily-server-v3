package com.project.daily.global.exeception.Handler;

import com.project.daily.global.exeception.ErrorResponse;
import com.project.daily.global.exeception.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> UserNotFoundExceptionHandler(HttpServletRequest request, UserNotFoundException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> EmailNotFoundExceptionHandler(HttpServletRequest request, EmailNotFoundException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(PasswordNotCorrectException.class)
    public ResponseEntity<ErrorResponse> PasswordNotCorrectExceptionHandler(HttpServletRequest request, PasswordNotCorrectException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UsedEmailException.class)
    public ResponseEntity<ErrorResponse> UsedEmailExceptionHandler(HttpServletRequest request, UsedEmailException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(KeyNotCorrectException.class)
    public ResponseEntity<ErrorResponse> KeyNotCorrectExceptionHandler(HttpServletRequest request, KeyNotCorrectException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> RefreshTokenNotFoundExceptionHandler(HttpServletRequest request, RefreshTokenNotFoundException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ErrorResponse> TokenInvalidException(HttpServletRequest request, TokenInvalidException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(RefreshTokenExpirationException.class)
    public ResponseEntity<ErrorResponse> RefreshTokenExpirationException(HttpServletRequest request, RefreshTokenExpirationException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ErrorResponse> TokenExpirationException(HttpServletRequest request, TokenExpirationException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> TokenNotFoundException(HttpServletRequest request, TokenNotFoundException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponse> handleDataException(HttpServletRequest request, HibernateException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UnknownServerException.class)
    public ResponseEntity<ErrorResponse> UnknownServerException(HttpServletRequest request, UnknownServerException e) {
        printException(request, e, e.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> processValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(" : ");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }

        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(", "));
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(" "));
        ErrorResponse errorResponse = new ErrorResponse(stringBuilder.toString(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public void printException(HttpServletRequest request, RuntimeException e, String message) {
        log.error(request.getRequestURI());
        log.error(message);
        e.printStackTrace();
    }
}
