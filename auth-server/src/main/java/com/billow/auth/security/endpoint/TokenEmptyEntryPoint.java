package com.billow.auth.security.endpoint;

import com.billow.auth.security.utils.SecurityUtils;
import com.billow.auth.supper.BaseResponse;
import com.billow.auth.supper.ResCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenEmptyEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Autowired
    public TokenEmptyEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        String resCode;
        int httpStatus;
        String errorCode;
        String message = e.getMessage();

        Throwable cause = e.getCause();
        if (cause instanceof InvalidTokenException) {
            resCode = ResCodeEnum.RESCODE_SIGNATURE_ERROR.getStatusCode();
            httpStatus = HttpStatus.UNAUTHORIZED.value();
            errorCode = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        } else {
            resCode = ResCodeEnum.RESCODE_NO_TOKEN_ERROR.getStatusCode();
            httpStatus = HttpStatus.BAD_REQUEST.value();
            errorCode = HttpStatus.BAD_REQUEST.getReasonPhrase();
        }
        BaseResponse baseResponse = SecurityUtils.getBaseResponse(resCode, httpStatus, errorCode, message);
        response.setStatus(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mapper.writeValue(response.getWriter(), baseResponse);
    }
}
