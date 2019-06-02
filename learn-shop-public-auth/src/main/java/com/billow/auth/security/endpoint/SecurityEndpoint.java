package com.billow.auth.security.endpoint;


import com.billow.auth.pojo.po.UserPo;
import com.billow.auth.security.properties.ClientProperties;
import com.billow.auth.security.properties.SecurityProperties;
import com.billow.auth.support.BaseResponse;
import com.billow.auth.support.ResCodeEnum;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetailsSource;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/15 14:32
 */
@FrameworkEndpoint
public class SecurityEndpoint {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private TokenExtractor tokenExtractor = new BearerTokenExtractor();
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new OAuth2AuthenticationDetailsSource();

    @Autowired
    private DefaultTokenServices defaultTokenServices;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 认证页面
     *
     * @return ModelAndView
     */
    @GetMapping("/authentication/require")
    public ModelAndView require() {
        return new ModelAndView("/login/index.html");
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/user")
    public Authentication user(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        OAuth2Authentication oAuth2Authentication = null;
        // 获取请求中中的 Authorization 或者 access_token 中的 token
        Authentication authentication = tokenExtractor.extract(req);
        if (authentication != null && !StringUtils.isEmpty(authentication.getPrincipal())) {
            // 从授权服务器，获取用户信息
            oAuth2Authentication = defaultTokenServices.loadAuthentication(authentication.getPrincipal().toString());
            if (oAuth2Authentication instanceof AbstractAuthenticationToken) {
                // 填充其它信息
                oAuth2Authentication.setDetails(authenticationDetailsSource.buildDetails(req));
                Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
                oAuth2Authentication.setAuthenticated(userAuthentication.isAuthenticated());
            }
        }
        return oAuth2Authentication;
    }

    @ResponseBody
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserPo userPo) {

        BaseResponse baseResponse = new BaseResponse();

        try {
            String username = userPo.getUsername();
            String password = userPo.getPassword();

            logger.info("Username:{},Password:{}", username, password);

            Assert.notNull(username, "用户名不能为空!");
            Assert.notNull(password, "密码不能为空!");

            ClientProperties client = securityProperties.getClient();

            String accessTokenUri = client.getAccessTokenUri();
            Assert.notNull(accessTokenUri, "accessTokenUri 不能为空,请配置 auth.security.client.accessTokenUri");
            String grantType = client.getGrantType();
            Assert.notNull(grantType, "grantType 不能为空,请配置 auth.security.client.grantType");
            String clientId = client.getClientId();
            Assert.notNull(clientId, "clientId 不能为空,请配置 auth.security.client.clientId");

            //        String url = "http://127.0.0.1:9999/oauth/token?grant_type=password&username=admin&password=123456&client_id=app";
            String url = "%s?grant_type=%s&username=%s&password=%s&client_id=%s";
            String trgUrl = String.format(url, accessTokenUri, grantType, username, password, clientId);

            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", "curl/7.58.0");
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<OAuth2AccessToken> accessTokenEntity = restTemplate.postForEntity(trgUrl, entity, OAuth2AccessToken.class);
            OAuth2AccessToken oAuth2AccessToken = accessTokenEntity.getBody();

            System.out.println(oAuth2AccessToken.getValue());
            System.out.println(oAuth2AccessToken.getRefreshToken().getValue());

            Map<String, String> result = new HashMap<>();
            result.put("accessToken", oAuth2AccessToken.getValue());
            result.put("refreshToken", oAuth2AccessToken.getRefreshToken().getValue());
            baseResponse.setResData(result);
            baseResponse.setResCode(ResCodeEnum.RESCODE_ASSESS_TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER);
        }


        return baseResponse;
    }
}
