package com.project.daily.domain.user.exeception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청*/
    USED_EMAIL(BAD_REQUEST, "이미 사용하는 이메일입니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    REFRESH_TOKEN_EXPIRATION(UNAUTHORIZED, "Refresh Token이 만료되었습니다."),

    /* 403 FORBIDDEN : 웹 페이지를 볼 권한이 없음 */
    TOKEN_INVALID(FORBIDDEN, "유효하지 않은 Token입니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "이메일을 찾을 수 없습니다."),
    PASSWORD_NOT_CORRECT(NOT_FOUND,"비밀번호가 맞지 않습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    USER_NOT_FIND(CONFLICT, "계정을 찾을 수 없습니다.");

    private HttpStatus httpStatus;
    private String msg;
}
