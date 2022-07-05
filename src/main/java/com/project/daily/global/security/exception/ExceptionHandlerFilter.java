package com.project.daily.global.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.daily.global.exeception.ErrorCode;
import com.project.daily.global.exeception.ErrorResponse;
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
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.info("[ ExceptionHandlerFilter ] 에서 ExpiredJwtException 발생");
            setErrorResponse(REFRESH_TOKEN_EXPIRATION, response);
        } catch (JwtException | IllegalArgumentException e) {
            log.info("[ ExceptionHandlerFilter ] 에서 JwtException 발생");
            setErrorResponse(TOKEN_INVALID, response);
        } catch (Exception e) {
            log.info("[ ExceptionHandlerFilter ] 에서 Exception 발생");
            setErrorResponse(UNKNOWN_SERVER_ERROR, response);
        }
    }

    public void setErrorResponse(ErrorCode errorCode, HttpServletResponse response) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=utf-8");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.name())
                .error(errorCode.getHttpStatus().name())
                .message(errorCode.getMsg())
                .build();

        String errorResponseEntityToJson = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(errorResponseEntityToJson);
    }
}
