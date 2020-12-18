package com.billow.gateway.security.config;

import cn.hutool.core.convert.Convert;
import com.billow.gateway.redis.RedisUtils;
import com.billow.gateway.security.constant.AuthConstant;
import com.billow.gateway.security.constant.RedisConstant;
import com.billow.gateway.security.vo.DataDictionaryVo;
import com.billow.gateway.security.vo.PermissionVo;
import com.billow.tools.constant.DictionaryType;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 */
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final static String PERMISSION = RedisCst.ROLE_PERMISSION_KEY;
    private final static String DICTIONARY = RedisCst.COMM_DICTIONARY_SYS_MODULE;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        String targetURI = uri.getPath();
        log.info("<<<=== targetURI:{}", targetURI);
        // 获取 redis 中数据字典的数据
        Map<String, String> dictionaryMap = redisUtils.getArray(DICTIONARY + DictionaryType.SYS_MC_SYSTEM_MODULE, DataDictionaryVo.class)
                .stream()
                .filter(f -> DictionaryType.SYS_FC_SYSTEM_MODULE.equals(f.getFieldType()))
                .collect(Collectors.toMap(DataDictionaryVo::getFieldValue, DataDictionaryVo::getFieldDisplay));

//        Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_KEY, uri.getPath());
//        List<String> authorities = Convert.toList(String.class, obj);
//        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(role -> {
                    role = role.replace(AuthConstant.AUTHORITY_PREFIX, "");
                    List<PermissionVo> permissionVos = redisUtils.getArray(PERMISSION + role, PermissionVo.class);
                    if (permissionVos == null) {
                        return false;
                    }
                    for (PermissionVo permissionVo : permissionVos) {
                        if (ToolsUtils.isEmpty(permissionVo.getSystemModule())) {
                            String sourceURI = permissionVo.getUrl();
                            log.info("===>>> sourceURI:{}", sourceURI);
                            if (antPathMatcher.match(sourceURI, targetURI)) {
                                log.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
                                return true;
                            }
                        } else {
                            String[] split = permissionVo.getSystemModule().split(",");
                            for (String s : split) {
                                String sourceURI = "/" + dictionaryMap.get(s) + permissionVo.getUrl();
                                log.info("===>>> sourceURI:{}", sourceURI);
                                if (antPathMatcher.match(sourceURI, targetURI)) {
                                    log.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}