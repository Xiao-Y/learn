package com.billow.auth.security.handler;

import com.billow.auth.security.utils.SecurityUtils;
import com.billow.auth.supper.BaseResponse;
import com.billow.auth.supper.ResCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 令牌不能访问该资源 （403）异常处理
 *
 * @author liuyongtao
 * @create 2018-11-19 10:49
 */
@Component
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    @Autowired
    public TokenAccessDeniedHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String resCode = ResCodeEnum.RESCODE_FORBIDDEN.getStatusCode();
        String message = accessDeniedException.getMessage();
        int httpStatus = HttpStatus.UNAUTHORIZED.value();
        String errorCode = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        BaseResponse baseResponse = SecurityUtils.getBaseResponse(resCode, httpStatus, errorCode, message);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        mapper.writeValue(response.getWriter(), baseResponse);
    }
}
