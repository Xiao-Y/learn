package com.billow.auth.endpoint;

import com.billow.auth.security.SecurityUtils;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
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
        String resCode = ResCodeEnum.RESCODE_NO_TOKEN_ERROR.getStatusCode();
        String message = "Access token is empty";
        BaseResponse baseResponse = SecurityUtils.getBaseResponse(resCode, message);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mapper.writeValue(response.getWriter(), baseResponse);
    }
}
