package com.project.daily.global.exeception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청*/
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    /* 403 FORBIDDEN : 웹 페이지를 볼 권한이 없음 */
    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */

    /* USER */
    USER_NOT_FOUND(CONFLICT, "계정을 찾을 수 없습니다."),
    EMAIL_NOT_FOUND(NOT_FOUND, "이메일을 찾을 수 없습니다."),
    PASSWORD_NOT_CORRECT(NOT_FOUND,"비밀번호가 맞지 않습니다."),
    USED_EMAIL(BAD_REQUEST, "이미 사용하는 이메일입니다."),
    NOT_VALIDATION_EMAIL(BAD_REQUEST, "이메일 형식이 아닙니다."),
    KEY_NOT_CORRECT(NOT_FOUND, "이메일 인증번호가 맞지 않습니다."),


    /* TOKEN */
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "RefreshToken을 찾을 수 없습니다."),
    TOKEN_INVALID(FORBIDDEN, "유효하지 않은 Token입니다."),
    REFRESH_TOKEN_EXPIRATION(UNAUTHORIZED, "Refresh Token이 만료되었습니다."),
    TOKEN_EXPIRATION(UNAUTHORIZED, "만료된 토큰입니다."),


    /* SERVER */
    HIBERNATE_ERROR(NOT_FOUND, "Hibernate 관련 오류 입니다."),
    UNKNOWN_SERVER_ERROR(INTERNAL_SERVER_ERROR, "알 수 없는 서버 오류 입니다.");


    private HttpStatus httpStatus;
    private String msg;
}
