package com.billow.tools.utlis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取用户信息工具
 *
 * @author liuyongtao
 * @create 2019-07-30 8:49
 */
@Component
public class UserTools {

    private Logger logger = LoggerFactory.getLogger(UserTools.class);

    public static String BEARER_TYPE = "Bearer";
    public static String ACCESS_TOKEN = "access_token";

    @Autowired
    protected HttpServletRequest request;

    /**
     * 获取当前用户 CODE
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/7/30 8:55
     */
    public String getCurrentUserCode() {
        String token = this.getAccessToken();
        if (token == null) {
            return null;
        }
        return this.getUserCode(token);
    }

    /**
     * 获取当前用户角色 CODE
     *
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/7/30 9:05
     */
    public List<String> getCurrentRoleCode() {
        String token = this.getAccessToken();
        if (token == null) {
            return new ArrayList<>();
        }
        return this.getRoleCode(token);
    }

    /**
     * 通过 token 获取用户 CODE
     *
     * @param token
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/7/30 8:55
     */
    public String getUserCode(String token) {
        return JwtUtils.getUserCodeByJwt(token);
    }

    /**
     * 通过 token 获取用户角色 CODE
     *
     * @param token
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/7/30 8:55
     */
    public List<String> getRoleCode(String token) {
        return JwtUtils.getRoleCodeByJwt(token);
    }

    /**
     * 获取 token
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/7/30 8:52
     */
    public String getAccessToken() {
        String token = this.getTokenByHeader();
        if (token == null) {
            token = this.getTokenByParameter();
        }
        if (token == null) {
            logger.warn("token is null");
        }
        return token;
    }

    /**
     * 获取 url 路径中的 access_token
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/7/29 16:23
     */
    private String getTokenByParameter() {
        if (request == null) {
            return null;
        }
        return request.getParameter(ACCESS_TOKEN);
    }

    /**
     * 获取 Header 中的 Authorization
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/7/29 16:23
     */
    private String getTokenByHeader() {
        if (request == null) {
            return null;
        }
        Enumeration<String> headers = request.getHeaders("Authorization");
        // 通常只有一个（大多数服务器都会强制这样做）
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
