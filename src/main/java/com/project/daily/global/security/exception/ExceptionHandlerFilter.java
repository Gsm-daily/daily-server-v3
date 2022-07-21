package com.project.daily.global.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.daily.global.exeception.ErrorCode;
import com.project.daily.global.exeception.ErrorResponse;
import com.project.daily.global.exeception.exceptions.TokenExpirationException;
import com.project.daily.global.exeception.exceptions.TokenInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.project.daily.global.exeception.ErrorCode.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            filterChain.doFilter(request, response); // 여기서 JwtRequestFilter로 간다.
        }catch (ExpiredJwtException e) {
            log.error("[ ExceptionHandlerFilter ] 에서 ExpiredJwtException 발생");
            throw new TokenExpirationException("expired Token", TOKEN_EXPIRATION);
        }catch (JwtException | IllegalArgumentException e) {
            log.error("[ ExceptionHandlerFilter ] 에서 JwtException 발생");
            throw new TokenInvalidException("invalid Token", TOKEN_INVALID);
        }
        /*
        catch (Exception e) {
            log.error("[ ExceptionHandlerFilter ] 에서 Exception 발생");
            setErrorResponse(UNKNOWN_SERVER_ERROR, response);
        }
         */
    }

//    public void setErrorResponse(ErrorCode errorCode, HttpServletResponse response) throws IOException {
//        response.setStatus(errorCode.getHttpStatus().value());
//        response.setContentType("application/json; charset=utf-8");
//
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .status(errorCode.getHttpStatus().value())
//                .error(errorCode.getHttpStatus().name()) // 예를 들어 Bad request 같은거?
//                .code(errorCode.name())
//                .message(errorCode.getMsg())
//                .build();
//
//        String errorResponseEntityToJson = objectMapper.writeValueAsString(errorResponse);
//        response.getWriter().write(errorResponseEntityToJson);
//
//    }
}
