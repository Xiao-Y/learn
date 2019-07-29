package com.billow.tools.utlis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import java.util.Map;

/**
 * JWT 相关工具类
 *
 * @author liuyongtao
 * @create 2019-07-29 16:16
 */
public class JwtUtils {

    private final static String USER_NAME = "user_name";

    /**
     * 解析 jwt 获取用户名
     *
     * @param token
     * @return void
     * @author LiuYongTao
     * @date 2019/7/29 16:17
     */
    public static String getUserNmaeByJwt(String token) {
        Jwt jwt = JwtHelper.decode(token);
        Object userName = JSONObject.parseObject(jwt.getClaims(), Map.class).get(USER_NAME);
        if (ToolsUtils.isNotEmpty(userName)) {
            return userName.toString();
        }
        return null;
    }
}
