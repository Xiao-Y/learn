package com.billow.zuul.api;

import com.billow.zuul.context.UserContext;
import com.billow.zuul.pojo.UserInfo;
import com.billow.zuul.pojo.UserRole;
import com.billow.zuul.security.auth.token.extractor.TokenExtractor;
import com.billow.zuul.security.auth.token.verifier.TokenVerifier;
import com.billow.zuul.security.config.TokenProperties;
import com.billow.zuul.security.config.WebSecurityConfig;
import com.billow.zuul.security.exception.InvalidTokenException;
import com.billow.zuul.security.token.Token;
import com.billow.zuul.security.token.TokenFactory;
import com.billow.zuul.security.token.impl.RawAccessToken;
import com.billow.zuul.security.token.impl.RefreshToken;
import com.billow.zuul.service.UserInfoService;
import com.billow.zuul.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public AuthController(TokenProperties tokenProperties, TokenVerifier tokenVerifier,
                          TokenFactory tokenFactory, TokenExtractor tokenExtractor,
                          UserInfoService userInfoService, UserRoleService userRoleService) {
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

    @PreAuthorize("hasAuthority('TEST')")
    @GetMapping("/api/test2")
    public String test2() {
        return "test2";
    }

    @PreAuthorize("hasAuthority('TEST')")
    @GetMapping("/api/test4")
    public String test4() {
        return "test4";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/manage/test3")
    public String test3() {
        return "test3";
    }

    @GetMapping("/api/auth/refresh_token")
    public Token refreshToken(HttpServletRequest request) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.TOKEN_HEADER_PARAM));
        RawAccessToken rawToken = new RawAccessToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, tokenProperties.getSigningKey())
                .orElseThrow(() -> new InvalidTokenException("Token验证失败"));

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidTokenException("Token验证失败");
        }

        String subject = refreshToken.getSubject();
        UserInfo user = Optional.ofNullable(userInfoService.findByName(subject))
                .orElseThrow(() -> new UsernameNotFoundException("用户未找到: " + subject));
        List<UserRole> roles = Optional.ofNullable(userRoleService.getRoleByUser(user))
                .orElseThrow(() -> new InsufficientAuthenticationException("用户没有分配角色"));
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUserName(), authorities);
        return tokenFactory.createAccessToken(userContext);
    }
}
