package com.billow.gateway.security.config;

import com.billow.gateway.security.constant.AuthConstant;
import com.billow.gateway.security.vo.PermissionVo;
import com.billow.redis.util.RedisUtils;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 */
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final static String PERMISSION = RedisCst.ROLE_PERMISSION_KEY;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        String targetURI = uri.getPath();
        log.info("<<<=== targetURI:{}", targetURI);
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(role -> {
                    role = role.replace(AuthConstant.AUTHORITY_PREFIX, "");
                    List<PermissionVo> permissionVos = redisUtils.getHash(PERMISSION, role, PermissionVo.class);
                    if (permissionVos == null) {
                        return false;
                    }
                    for (PermissionVo permissionVo : permissionVos) {
                        String systemModule = StringUtils.isBlank(permissionVo.getSystemModule()) ? "" : "/" + permissionVo.getSystemModule();
                        String sourceURI = systemModule + permissionVo.getUrl();
                        log.info("===>>> sourceURI:{}", sourceURI);
                        if (antPathMatcher.match(sourceURI, targetURI)) {
                            return true;
                        }
                    }
                    log.info("===>>> 没有匹配到对应路径，匹配失败 <<<===\n");
                    return false;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}