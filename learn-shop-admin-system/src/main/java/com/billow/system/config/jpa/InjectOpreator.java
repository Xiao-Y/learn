package com.billow.system.config.jpa;

import com.alibaba.fastjson.JSONObject;
import com.billow.tools.utlis.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import springfox.documentation.spi.service.contexts.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * 给Bean中的 @CreatedBy  @LastModifiedBy 注入操作人
 *
 * @author LiuYongTao
 * @date 2019/7/16 14:29
 */
@Configuration
public class InjectOpreator implements AuditorAware<String> {

    public static String BEARER_TYPE = "Bearer";
    public static String ACCESS_TOKEN = "access_token";

    @Autowired
    protected HttpServletRequest request;


    @Override
    public String getCurrentAuditor() {
        String token = this.getTokenByHeader();
        if (token == null) {
            token = this.getTokenByParameter();
        }
        if (token == null) {
            return null;
        }

        return JwtUtils.getUserNmaeByJwt(token);
    }

    /**
     * 获取 url 路径中的 access_token
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/7/29 16:23
     */
    private String getTokenByParameter() {
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