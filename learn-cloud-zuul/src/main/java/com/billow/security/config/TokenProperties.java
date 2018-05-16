package com.billow.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 加载application.yml中以billow.security.token开头的数据
 *
 * @author LiuYongTao
 * @date 2018/5/14 14:55
 */
@Configuration
@ConfigurationProperties(prefix = "billow.security.token")
public class TokenProperties {
    //token的过期时间
    private Integer expirationTime;
    //发行人
    private String issuer;
    //使用的签名KEY {@link com.billow.security.token.Token}.
    private String signingKey;
    //{@link com.billow.security.token.Token} 刷新过期时间
    private Integer refreshExpTime;

    /**
     * {@link com.billow.security.token.Token} token的过期时间
     *
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2018/5/14 14:56
     */
    public Integer getExpirationTime() {
        return expirationTime;
    }

    /**
     * {@link com.billow.security.token.Token} token的过期时间
     *
     * @param expirationTime 过期时间
     * @return void
     * @author LiuYongTao
     * @date 2018/5/14 14:57
     */
    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * 发行人
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2018/5/14 14:57
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * 发行人
     *
     * @param issuer 发行人
     * @return void
     * @author LiuYongTao
     * @date 2018/5/14 14:59
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    /**
     * 使用的签名KEY {@link com.billow.security.token.Token}.
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2018/5/14 14:57
     */
    public String getSigningKey() {
        return signingKey;
    }

    /**
     * 使用的签名KEY {@link com.billow.security.token.Token}.
     *
     * @param signingKey 使用的签名KEY
     * @return void
     * @author LiuYongTao
     * @date 2018/5/14 14:57
     */
    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    /**
     * {@link com.billow.security.token.Token} 刷新过期时间
     *
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2018/5/14 14:58
     */
    public Integer getRefreshExpTime() {
        return refreshExpTime;
    }

    /**
     * {@link com.billow.security.token.Token} 刷新过期时间
     *
     * @param refreshExpTime 刷新过期时间
     * @return void
     * @author LiuYongTao
     * @date 2018/5/14 14:58
     */
    public void setRefreshExpTime(Integer refreshExpTime) {
        this.refreshExpTime = refreshExpTime;
    }

}

