package com.billow.zuul.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetailsSource;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用于授权码访问时，解析access_token（暂时这样，还没有找到好的方法）
 *
 * @author LiuYongTao
 * @date 2019/5/10 16:31
 * @see org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter OAuth2AuthenticationProcessingFilter
 * @see org.springframework.security.oauth2.provider.token.RemoteTokenServices  ResourceServerTokenServices
 * @see org.springframework.security.web.context.HttpSessionSecurityContextRepository HttpSessionSecurityContextRepository
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    public static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";
    private String springSecurityContextKey = SPRING_SECURITY_CONTEXT_KEY;

    private TokenExtractor tokenExtractor = new BearerTokenExtractor();
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new OAuth2AuthenticationDetailsSource();
    private AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
    private AuthenticationEventPublisher eventPublisher = new NullEventPublisher();

    private ResourceServerTokenServices resourceServerTokenServices;

    public JWTAuthenticationFilter(ResourceServerTokenServices resourceServerTokenServices) {
        this.resourceServerTokenServices = resourceServerTokenServices;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            // 获取请求中中的 Authorization 或者 access_token 中的 token
            Authentication authentication = tokenExtractor.extract(req);
            // 只有在授权码模式访问时，才会有以下处理
            if (authentication != null && !StringUtils.isEmpty(authentication.getPrincipal())) {
                // 从授权服务器，获取用户信息（远程 RemoteTokenServices）
                OAuth2Authentication oAuth2Authentication = resourceServerTokenServices.loadAuthentication(authentication.getPrincipal().toString());
                req.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE, authentication.getPrincipal());
                if (oAuth2Authentication instanceof AbstractAuthenticationToken) {
                    // 填充其它信息
                    oAuth2Authentication.setDetails(authenticationDetailsSource.buildDetails(req));
                    Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
                    oAuth2Authentication.setAuthenticated(userAuthentication.isAuthenticated());
                }
                // 放入到 session 中，HttpSessionSecurityContextRepository 的 loadContext 会从中获取用户信息
                HttpSession httpSession = req.getSession();
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(oAuth2Authentication);
                httpSession.setAttribute(springSecurityContextKey, context);

                eventPublisher.publishAuthenticationSuccess(oAuth2Authentication);
            }
        } catch (OAuth2Exception failed) {
            SecurityContextHolder.clearContext();
            eventPublisher.publishAuthenticationFailure(new BadCredentialsException(failed.getMessage(), failed),
                    new PreAuthenticatedAuthenticationToken("access-token", "N/A"));
            authenticationEntryPoint.commence(req, res,
                    new InsufficientAuthenticationException(failed.getMessage(), failed));
            return;
        }
        filterChain.doFilter(req, res);
    }

    private static final class NullEventPublisher implements AuthenticationEventPublisher {
        public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        }

        public void publishAuthenticationSuccess(Authentication authentication) {
        }
    }
}