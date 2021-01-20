package com.billow.tools.utlis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JWT 相关工具类
 *
 * @author liuyongtao
 * @create 2019-07-29 16:16
 */
public class JwtUtils {

    private final static String USER_NAME = "user_name";
    private final static String AUTHORITIES = "authorities";

    /**
     * 解析 jwt 获取用户名
     *
     * @param token
     * @return void
     * @author LiuYongTao
     * @date 2019/7/29 16:17
     */
    public static String getUserCodeByJwt(String token) {
        Jwt jwt = JwtHelper.decode(token);
        Object userCode = JSONObject.parseObject(jwt.getClaims(), Map.class).get(USER_NAME);
        if (ToolsUtils.isNotEmpty(userCode)) {
            return userCode.toString();
        }
        return null;
    }

    /**
     * 解析 jwt 获取角色 CODE
     *
     * @param token
     * @return void
     * @author LiuYongTao
     * @date 2019/7/29 16:17
     */
    public static List<String> getRoleCodeByJwt(String token) {
        if (ToolsUtils.isEmpty(token)) {
            return new ArrayList<>();
        }
        Jwt jwt = JwtHelper.decode(token);
        Object roleCode = JSONObject.parseObject(jwt.getClaims(), Map.class).get(AUTHORITIES);
        if (roleCode != null) {
            return ((JSONArray) roleCode).toJavaList(String.class);
        }
        return new ArrayList<>();
    }
}
