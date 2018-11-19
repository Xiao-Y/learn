package com.billow.auth.endpoint;

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
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Autowired
    public RestAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
        baseResponse.setResCode(ResCodeEnum.RESCODE_UNAUTHORIZED.getStatusCode());
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Authentication failed");
        map.put("errorCode", HttpStatus.UNAUTHORIZED.value());
        map.put("httpStatus", HttpStatus.UNAUTHORIZED);
        baseResponse.setResData(map);
        mapper.writeValue(response.getWriter(), baseResponse);
    }
}
