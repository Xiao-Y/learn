package com.billow.gateway.security.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.billow.gateway.security.vo.UserDto;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Slf4j
@Component
public class LoginUserHolder {

    /**
     * 获取登录用户信息
     *
     * @param request
     * @return
     */
    public UserDto getCurrentUser(ServerHttpRequest request) {
        UserDto userDto = new UserDto();
        //从Header中获取用户token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("Authorization");
        log.info("获取到的Authorization:{}", token);
        if (StrUtil.isEmpty(token)) {
            return userDto;
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            log.info("user:{}", userStr);
            JSONObject userJsonObject = new JSONObject(userStr);
            userDto.setId(Convert.toLong(userJsonObject.get("id")));
            userDto.setUsername(userJsonObject.getStr("user_name"));
            userDto.setUsercode(userJsonObject.getStr("usercode"));
            userDto.setRoles(Convert.toList(String.class, userJsonObject.get("authorities")));
        } catch (ParseException e) {
            log.error("获取用户异常:{}", e);
        }
        return userDto;
    }
}