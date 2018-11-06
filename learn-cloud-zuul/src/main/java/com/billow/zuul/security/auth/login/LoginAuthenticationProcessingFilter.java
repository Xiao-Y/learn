package com.billow.zuul.security.auth.login;

import com.billow.zuul.security.exception.AuthMethodNotSupportedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录处理过滤器
 */
public class LoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static Logger logger = LoggerFactory.getLogger(LoginAuthenticationProcessingFilter.class);

    // AwareAuthenticationSuccessHandler
    private final AuthenticationSuccessHandler successHandler;
    // AwareAuthenticationFailureHandler
    private final AuthenticationFailureHandler failureHandler;

    public LoginAuthenticationProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler,
                                               AuthenticationFailureHandler failureHandler) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            logger.debug("Authentication method not supported. Request method: " + request.getMethod());
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LoginRequest loginRequest = new LoginRequest(username, password);
        if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword());
        // 调用合适的处理器，（public boolean supports(Class<?> authentication)）
        return this.getAuthenticationManager().authenticate(token);
    }

    /**
     * 在LoginAuthenticationProvider 处理完成后，成功后的处理
     *
     * @param [request, response, chain, authResult]
     * @return void
     * @author LiuYongTao
     * @date 2018/5/17 8:46
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    /**
     * 在LoginAuthenticationProvider 处理完成后，失败后的处理
     *
     * @param failed 失败异常信息
     * @return void
     * @author LiuYongTao
     * @date 2018/5/17 8:47
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}

