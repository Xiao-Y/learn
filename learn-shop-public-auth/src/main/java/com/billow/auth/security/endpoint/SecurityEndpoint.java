package com.billow.auth.security.endpoint;


import com.billow.auth.pojo.po.UserPo;
import com.billow.auth.security.properties.ClientProperties;
import com.billow.auth.security.properties.SecurityProperties;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
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
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

//    /**
//     * 认页面
//     *
//     * @return ModelAndView
//     */
//    @GetMapping("/authentication/require")
//    public ModelAndView require() {
//        return new ModelAndView("/index.html");
//    }

//    /**
//     * 获取用户信息
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @ResponseBody
//    @GetMapping("/user")
//    public Authentication user(ServletRequest request, ServletResponse response) {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        OAuth2Authentication oAuth2Authentication = null;
//        // 获取请求中中的 Authorization 或者 access_token 中的 token
//        Authentication authentication = tokenExtractor.extract(req);
//        if (authentication != null && !StringUtils.isEmpty(authentication.getPrincipal())) {
//            // 从授权服务器，获取用户信息
//            oAuth2Authentication = defaultTokenServices.loadAuthentication(authentication.getPrincipal().toString());
//            if (oAuth2Authentication instanceof AbstractAuthenticationToken) {
//                // 填充其它信息
//                oAuth2Authentication.setDetails(authenticationDetailsSource.buildDetails(req));
//                Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
//                oAuth2Authentication.setAuthenticated(userAuthentication.isAuthenticated());
//            }
//        }
//        return oAuth2Authentication;
//    }

    @Autowired
    private TokenStore tokenStore;

    /**
     * 获取用户信息
     *
     * @param authorization
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/11/12 13:57
     */
    @RequestMapping("/user")
    @ResponseBody
    public Map<String, Object> user(@RequestHeader String authorization) {
        //必须通过客户端{携带的token在服务端的token存储中获取用户信息。
        //header中 Authorization传过来的格式为[type token]的格式
        //因此必须先对Authorization传过来的数据进行分隔authorization.split(" ")[1]才是真正的token
        Map<String, Object> map = new HashMap<>();
        OAuth2Authentication authen = null;
        try {
            authen = tokenStore.readAuthentication(authorization.split(" ")[1]);
            if (authen == null) {
                map.put("error", "invalid token !");
                return map;
            }
        } catch (Exception e) {
            System.out.println(e);
            map.put("error", e);
            return map;
        }
        //注意这两个key都不能随便填，都是和客户端进行数据处理时进行对应的。
        map.put("user", authen.getPrincipal());
        map.put("authorities", authen.getAuthorities());
        return map;

    }

    /**
     * 用于前后分离时登陆
     *
     * @param userPo
     * @return
     */
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
            String clientSecret = client.getClientSecret();
            Assert.notNull(clientSecret, "clientSecret 不能为空,请配置 auth.security.client.clientSecret");
            String scope = client.getScope();
            Assert.notNull(scope, "scope 不能为空,请配置 auth.security.client.scope");

            // String url = "http://127.0.0.1:9999/oauth/token?grant_type=password&username=admin&password=123456&client_id=app&client_secret=app&scope=app";
            String url = "%s?grant_type=%s&username=%s&password=%s&client_id=%s&client_secret=%s&scope=%s";
            String trgUrl = String.format(url, accessTokenUri, grantType, username, password, clientId, clientSecret, scope);

            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", "curl/7.58.0");
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<OAuth2AccessToken> accessTokenEntity = restTemplate.postForEntity(trgUrl, entity, OAuth2AccessToken.class);
            OAuth2AccessToken oAuth2AccessToken = accessTokenEntity.getBody();

            logger.info("accessToken:{}", oAuth2AccessToken.getValue());
            logger.info("refreshToken:{}", oAuth2AccessToken.getRefreshToken().getValue());

            Map<String, String> result = new HashMap<>();
            result.put("accessToken", oAuth2AccessToken.getValue());
            result.put("refreshToken", oAuth2AccessToken.getRefreshToken().getValue());
            baseResponse.setResData(result);
            baseResponse.setResCode(ResCodeEnum.RESCODE_ASSESS_TOKEN);
        } catch (Exception e) {
            logger.error("登陆异常：{}", e);
            baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER);
        }
        return baseResponse;
    }
}
