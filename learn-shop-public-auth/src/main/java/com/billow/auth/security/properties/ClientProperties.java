package com.billow.auth.security.properties;

/**
 * 前后分离时,获取 token 必要参数
 *
 * @author liuyongtao
 * @create 2019-05-23 9:09
 */
public class ClientProperties {
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String accessTokenUri;

    public String getClientId() {
        return clientId;
    }

    public ClientProperties setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public ClientProperties setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public ClientProperties setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public ClientProperties setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
        return this;
    }
}
