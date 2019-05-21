package com.billow.auth.security.endpoint;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetailsSource;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private DefaultTokenServices defaultTokenServices;
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new OAuth2AuthenticationDetailsSource();

    /**
     * 认证页面
     *
     * @return ModelAndView
     */
    @GetMapping("/authentication/require")
    public ModelAndView require() {
        return new ModelAndView("/login/index.html");
    }

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
}
