package com.billow.auth.security.handler;

import com.billow.auth.properties.SecurityProperties;
import com.billow.auth.supper.BaseResponse;
import com.billow.auth.supper.ResCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("authenctiationFailureHandler")
public class CustomAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String resCode = ResCodeEnum.RESCODE_OTHER_ERROR.getStatusCode();

        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            resCode = ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusCode();
        }
        logger.info("登录失败:{}", ResCodeEnum.getResCodeEnum(resCode));

        response.setContentType("application/json;charset=UTF-8");

        BaseResponse<String> baseResponse = new BaseResponse(resCode);
        baseResponse.setResData(exception.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("ExceptionType", exception.getClass().getName());
        baseResponse.setExt(map);
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}