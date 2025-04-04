package com.team4.global.handler;

import com.team4.global.exception.ExceptionSituation;
import com.team4.global.response.HttpResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        log.warn("Unauthorized: ", authException);

        ExceptionSituation exception = new ExceptionSituation("인증되지않은 사용자입니다. 로그인후 이용해주세요.", HttpStatus.FORBIDDEN, 7001);
        HttpResponseUtil.setErrorResponse(response, HttpStatus.FORBIDDEN, exception);
    }
}
