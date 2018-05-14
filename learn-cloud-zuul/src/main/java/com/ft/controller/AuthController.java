package com.ft.controller;

import com.ft.context.UserContext;
import com.ft.pojo.UserInfo;
import com.ft.pojo.UserRole;
import com.ft.security.auth.token.extractor.TokenExtractor;
import com.ft.security.auth.token.verifier.TokenVerifier;
import com.ft.security.config.TokenProperties;
import com.ft.security.config.WebSecurityConfig;
import com.ft.security.exception.InvalidTokenException;
import com.ft.security.token.TokenFactory;
import com.ft.security.token.Token;
import com.ft.security.token.impl.RawAccessToken;
import com.ft.security.token.impl.RefreshToken;
import com.ft.service.UserInfoService;
import com.ft.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 测试数据为admin 和 test 密码随便
 *
 * @author LiuYongTao
 * @date 2018/5/14 15:28
 */
@RestController
public class AuthController {

    private final TokenProperties tokenProperties;
    private final TokenVerifier tokenVerifier;
    private final TokenFactory tokenFactory;
    private final TokenExtractor tokenExtractor;
    private final UserInfoService userInfoService;
    private final UserRoleService userRoleService;

    @Autowired
    public AuthController(TokenProperties tokenProperties, TokenVerifier tokenVerifier, TokenFactory tokenFactory, TokenExtractor tokenExtractor, UserInfoService userInfoService, UserRoleService userRoleService) {
        this.tokenProperties = tokenProperties;
        this.tokenVerifier = tokenVerifier;
        this.tokenFactory = tokenFactory;
        this.tokenExtractor = tokenExtractor;
        this.userInfoService = userInfoService;
        this.userRoleService = userRoleService;
    }

    @PostMapping("/api/auth/login")
    public String login(HttpServletRequest request) {
        return "login";
    }

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @GetMapping("/api/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/manage/test3")
    public String test3() {
        return "test3";
    }

    @GetMapping("/api/auth/refresh_token")
    public Token refreshToken(HttpServletRequest request) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.TOKEN_HEADER_PARAM));
        RawAccessToken rawToken = new RawAccessToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, tokenProperties.getSigningKey()).orElseThrow(() -> new InvalidTokenException("Token验证失败"));

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidTokenException("Token验证失败");
        }

        String subject = refreshToken.getSubject();
        UserInfo user = Optional.ofNullable(userInfoService.findByName(subject)).orElseThrow(() -> new UsernameNotFoundException("用户未找到: " + subject));
        List<UserRole> roles = Optional.ofNullable(userRoleService.getRoleByUser(user)).orElseThrow(() -> new InsufficientAuthenticationException("用户没有分配角色"));
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUserName(), authorities);
        return tokenFactory.createAccessToken(userContext);
    }
}
