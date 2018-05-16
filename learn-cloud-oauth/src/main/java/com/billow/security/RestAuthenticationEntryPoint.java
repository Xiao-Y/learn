package com.billow.security;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
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
 * 当用户请求了一个受保护的资源，但是用户没有通过认证
 *
 * @author LiuYongTao
 * @date 2018/5/16 18:38
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String resTimestamp = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resCode", HttpStatus.UNAUTHORIZED.value());
        jsonObject.put("resMsg", ex.getMessage());
        jsonObject.put("resTimestamp", resTimestamp);

        response.getWriter().write(jsonObject.toJSONString());
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }
}
