package com.billow.gateway.security.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.billow.gateway.security.properties.ClientProperties;
import com.billow.gateway.security.properties.SecurityProperties;
import com.billow.gateway.security.vo.TokenVo;
import com.billow.gateway.security.vo.UserVo;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;

@Slf4j
@Component
public class Oauth2Holder {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取登录用户信息
     *
     * @param request
     * @return
     */
    public UserVo getCurrentUser(ServerHttpRequest request) {
        UserVo userVo = new UserVo();
        //从Header中获取用户token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("Authorization");
        log.info("获取到的Authorization:{}", token);
        if (StrUtil.isEmpty(token)) {
            return userVo;
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            log.info("user:{}", userStr);
            JSONObject userJsonObject = new JSONObject(userStr);
            userVo.setId(Convert.toLong(userJsonObject.get("id")));
            userVo.setUsername(userJsonObject.getStr("user_name"));
            userVo.setUsercode(userJsonObject.getStr("usercode"));
            userVo.setRoles(Convert.toList(String.class, userJsonObject.get("authorities")));
        } catch (ParseException e) {
            log.error("获取用户异常:{}", e);
        }
        return userVo;
    }

    /**
     * 通过用户名和密码获取token
     *
     * @param username: 用户名
     * @param password: 密码
     * @author billow
     * @Date 2020/12/20 11:57
     * @return: com.billow.gateway.security.vo.TokenVo
     **/
    public TokenVo getTokenByUsernameAndPassword(String username, String password) {
        ClientProperties client = securityProperties.getClient();
//
        String accessTokenUri = client.getAccessTokenUri();
        Assert.notNull(accessTokenUri, "accessTokenUri 不能为空,请配置 secure.client.accessTokenUri");
        String grantType = client.getGrantType();
        Assert.notNull(grantType, "grantType 不能为空,请配置 secure.client.grantType");
        String clientId = client.getClientId();
        Assert.notNull(clientId, "clientId 不能为空,请配置 secure.client.clientId");
        String clientSecret = client.getClientSecret();
        Assert.notNull(clientSecret, "clientSecret 不能为空,请配置 secure.client.clientSecret");
        String scope = client.getScope();
        Assert.notNull(scope, "scope 不能为空,请配置 secure.client.scope");

        // String url = "http://learn-shop-public-auth/oauth/token?grant_type=password&username=admin&password=admin&client_id=webapp&client_secret=webapp&scope=webapp";
        String url = "%s?grant_type=%s&username=%s&password=%s&client_id=%s&client_secret=%s&scope=%s";
        String trgUrl = String.format(url, accessTokenUri, grantType, username, password, clientId, clientSecret, scope);

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "curl/7.58.0");
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TokenVo> accessTokenEntity = restTemplate.postForEntity(trgUrl, entity, TokenVo.class);
        TokenVo tokenVo = accessTokenEntity.getBody();
        log.info("==========>>>>tokentVo:{}", tokenVo);
        return tokenVo;
    }

    /**
     * 通过刷新token 获取token
     *
     * @param refreshToken: 刷新token
     * @author billow
     * @Date 2020/12/20 12:01
     * @return: com.billow.gateway.security.vo.TokenVo
     **/
    public TokenVo getTokenByRefreshToken(String refreshToken) {
        ClientProperties client = securityProperties.getClient();
//
        String accessTokenUri = client.getAccessTokenUri();
        Assert.notNull(accessTokenUri, "accessTokenUri 不能为空,请配置 secure.client.accessTokenUri");
        String grantType = "refresh_token";
        String clientId = client.getClientId();
        Assert.notNull(clientId, "clientId 不能为空,请配置 secure.client.clientId");
        String clientSecret = client.getClientSecret();
        Assert.notNull(clientSecret, "clientSecret 不能为空,请配置 secure.client.clientSecret");
        String scope = client.getScope();
        Assert.notNull(scope, "scope 不能为空,请配置 secure.client.scope");

        // String url = "http://127.0.0.1:8771/public-auth/oauth/token?grant_type=&scope=webapp&client_id=webapp&client_secret=webapp&refresh_token={{refreshToken}}";
        String url = "%s?grant_type=%s&client_id=%s&client_secret=%s&scope=%s&refresh_token=%s";
        String trgUrl = String.format(url, accessTokenUri, grantType, clientId, clientSecret, scope, refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "curl/7.58.0");
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TokenVo> accessTokenEntity = restTemplate.postForEntity(trgUrl, entity, TokenVo.class);
        TokenVo tokenVo = accessTokenEntity.getBody();
        log.info("==========>>>>tokentVo:{}", tokenVo);
        return tokenVo;
    }
}