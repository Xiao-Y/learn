package com.billow.mybatis.utils;

import com.billow.tools.utlis.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 获取用户信息工具
 *
 * @author liuyongtao
 * @create 2019-07-30 8:49
 */
public class MybatisUserTools {

    private Logger logger = LoggerFactory.getLogger(MybatisUserTools.class);

    public static String BEARER_TYPE = "Bearer";
    public static String ACCESS_TOKEN = "access_token";

    @Autowired(required = false)
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
        try {
            return request.getParameter(ACCESS_TOKEN);
        } catch (Exception e) {
            logger.warn("getTokenByParameter 获取 token 信息异常");
        }
        return null;
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
        try {
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
        } catch (Exception e) {
            logger.warn("getTokenByHeader 获取用户信息异常");
        }
        return null;
    }
}
