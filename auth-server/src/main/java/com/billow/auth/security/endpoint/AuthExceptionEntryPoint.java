package com.billow.auth.security.endpoint;

import com.billow.auth.security.utils.SecurityUtils;
import com.billow.auth.supper.BaseResponse;
import com.billow.auth.supper.ResCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常
 *
 * @author liuyongtao
 * @create 2018-11-24 13:02
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Autowired
    public AuthExceptionEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String resCode = ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusCode();
        String message = e.getMessage();
        int httpStatus = HttpStatus.UNAUTHORIZED.value();
        String errorCode = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        BaseResponse baseResponse = SecurityUtils.getBaseResponse(resCode, httpStatus, errorCode, message);

        response.setStatus(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mapper.writeValue(response.getWriter(), baseResponse);
    }
}
