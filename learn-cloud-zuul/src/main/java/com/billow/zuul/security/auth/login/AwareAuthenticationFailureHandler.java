package com.billow.zuul.security.auth.login;

import com.billow.zuul.resData.BaseResponse;
import com.billow.zuul.resData.ResCodeEnum;
import com.billow.zuul.security.common.ErrorCode;
import com.billow.zuul.security.exception.AuthMethodNotSupportedException;
import com.billow.zuul.security.exception.ExpiredTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;

    @Autowired
    public AwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        BaseResponse<Map<String,Object>> baseResponse = new BaseResponse<>();
        baseResponse.setResCode(ResCodeEnum.RESCODE_UNAUTHORIZED.getStatusCode());
        Map<String,Object> map = new HashMap<>();
        map.put("message","Authentication failed");
        map.put("errorCode", ErrorCode.AUTHENTICATION);
        map.put("httpStatus",HttpStatus.UNAUTHORIZED);


        if (e instanceof BadCredentialsException) {
            map.put("message","Invalid username or password");
//            mapper.writeValue(response.getWriter(), ErrorResponse.of("Invalid username or password", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        } else if (e instanceof ExpiredTokenException) {
            map.put("message","Token has expired");
//            mapper.writeValue(response.getWriter(), ErrorResponse.of("Token has expired", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
        } else if (e instanceof AuthMethodNotSupportedException) {
            map.put("message",e.getMessage());
//            mapper.writeValue(response.getWriter(), ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        }else if (e instanceof UsernameNotFoundException) {
            map.put("message",e.getMessage());
//            mapper.writeValue(response.getWriter(), ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        }else if (e instanceof InsufficientAuthenticationException) {
            map.put("message",e.getMessage());
//            mapper.writeValue(response.getWriter(), ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        }
        map.put("errorCode",ErrorCode.AUTHENTICATION);
        map.put("httpStatus",HttpStatus.UNAUTHORIZED);
        baseResponse.setResData(map);
        mapper.writeValue(response.getWriter(), baseResponse);
    }
}
