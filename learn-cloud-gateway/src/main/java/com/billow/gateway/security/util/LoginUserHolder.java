package com.billow.gateway.security.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.billow.gateway.security.vo.UserVo;
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
    public UserVo getCurrentUser(ServerHttpRequest request) {
        UserVo userVo = new UserVo();
        //从Header中获取用户token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("Authorization");
        log.info("获取到的Authorization:{}", token);
        if (StrUtil.isEmpty(token)) {
            return userVo;
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            log.info("user:{}", userStr);
            JSONObject userJsonObject = new JSONObject(userStr);
            userVo.setId(Convert.toLong(userJsonObject.get("id")));
            userVo.setUsername(userJsonObject.getStr("user_name"));
            userVo.setUsercode(userJsonObject.getStr("usercode"));
            userVo.setRoles(Convert.toList(String.class, userJsonObject.get("authorities")));
        } catch (ParseException e) {
            log.error("获取用户异常:{}", e);
        }
        return userVo;
    }
}