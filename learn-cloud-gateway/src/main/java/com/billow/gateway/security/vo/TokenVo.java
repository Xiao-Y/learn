package com.billow.gateway.security.vo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回token 信息
 *
 * @author billow
 * @Date 2020/12/20 10:28
 **/
@Data
public class TokenVo  implements Serializable {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String scope;
    private Long expiresIn;
    private String jti;

    // 自己扩展
    private Integer id;
    private String usercode;

    @JsonGetter("tokenType")
    public String getTokenType() {
        return tokenType;
    }

    @JsonSetter("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @JsonGetter("accessToken")
    public String getAccessToken() {
        return accessToken;
    }

    @JsonSetter("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonGetter("refreshToken")
    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonSetter("refresh_token")
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @JsonGetter("expiresIn")
    public Long getExpiresIn() {
        return expiresIn;
    }

    @JsonSetter("expires_in")
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
