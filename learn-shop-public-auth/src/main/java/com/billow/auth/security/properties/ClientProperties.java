package com.billow.auth.security.properties;

import lombok.Data;

/**
 * 前后分离时,获取 token 必要参数
 *
 * @author liuyongtao
 * @create 2019-05-23 9:09
 */
@Data
public class ClientProperties {

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String accessTokenUri;
    private String scope;
}
