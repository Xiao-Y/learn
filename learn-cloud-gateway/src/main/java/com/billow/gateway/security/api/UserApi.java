package com.billow.gateway.security.api;

import com.billow.gateway.security.properties.ClientProperties;
import com.billow.gateway.security.properties.SecurityProperties;
import com.billow.gateway.security.util.LoginUserHolder;
import com.billow.gateway.security.vo.TokenVo;
import com.billow.gateway.security.vo.UserVo;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuyongtao
 * @since 2020-12-17 9:41
 */
@Slf4j
@RestController
@RequestMapping("/userApi")
public class UserApi {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoginUserHolder loginUserHolder;

    @GetMapping("/currentUser")
    public UserVo currentUser(ServerHttpRequest request) {
        return loginUserHolder.getCurrentUser(request);
    }

    /**
     * 用于前后分离时登陆
     *
     * @param userVo
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserVo userVo) {

        BaseResponse baseResponse = new BaseResponse();

        try {
            String username = userVo.getUsername();
            String password = userVo.getPassword();

            log.info("Username:{},Password:{}", username, password);

            Assert.notNull(username, "用户名不能为空!");
            Assert.notNull(password, "密码不能为空!");

            ClientProperties client = securityProperties.getClient();
//
            String accessTokenUri = client.getAccessTokenUri();
            Assert.notNull(accessTokenUri, "accessTokenUri 不能为空,请配置 auth.security.client.accessTokenUri");
            String grantType = client.getGrantType();
            Assert.notNull(grantType, "grantType 不能为空,请配置 auth.security.client.grantType");
            String clientId = client.getClientId();
            Assert.notNull(clientId, "clientId 不能为空,请配置 auth.security.client.clientId");
            String clientSecret = client.getClientSecret();
            Assert.notNull(clientSecret, "clientSecret 不能为空,请配置 auth.security.client.clientSecret");
            String scope = client.getScope();
            Assert.notNull(scope, "scope 不能为空,请配置 auth.security.client.scope");

            // String url = "http://learn-shop-public-auth/oauth/token?grant_type=password&username=admin&password=admin&client_id=webapp&client_secret=webapp&scope=webapp";
            String url = "%s?grant_type=%s&username=%s&password=%s&client_id=%s&client_secret=%s&scope=%s";
            String trgUrl = String.format(url, accessTokenUri, grantType, username, password, clientId, clientSecret, scope);

            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", "curl/7.58.0");
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<TokenVo> accessTokenEntity = restTemplate.postForEntity(trgUrl, entity, TokenVo.class);
            TokenVo tokenVo = accessTokenEntity.getBody();

            log.info("accessToken:{}", tokenVo.getAccessToken());
            log.info("refreshToken:{}", tokenVo.getRefreshToken());
            baseResponse.setResData(tokenVo);
            baseResponse.setResCode(ResCodeEnum.RESCODE_ASSESS_TOKEN);
        } catch (Exception e) {
            log.error("登陆异常：{}", e);
            baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER);
        }
        return baseResponse;
    }
}
