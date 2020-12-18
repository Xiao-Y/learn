package com.billow.gateway.security.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.billow.gateway.security.vo.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class LoginUserHolder {

    /**
     * 获取登录用户信息
     *
     * @param request
     * @return
     */
    public UserDto getCurrentUser(ServerHttpRequest request) {
        //从Header中获取用户信息
        HttpHeaders headers = request.getHeaders();
        String userStr = headers.getFirst("user");
        JSONObject userJsonObject = new JSONObject(userStr);
        UserDto userDTO = new UserDto();
        userDTO.setUsername(userJsonObject.getStr("user_name"));
        userDTO.setId(Convert.toLong(userJsonObject.get("id")));
        userDTO.setRoles(Convert.toList(String.class, userJsonObject.get("authorities")));
        return userDTO;
    }
}