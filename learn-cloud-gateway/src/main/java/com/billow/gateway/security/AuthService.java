//package com.billow.gateway.security;
//
//
//import cn.hutool.core.convert.Convert;
//import com.billow.gateway.security.vo.DataDictionaryVo;
//import com.billow.gateway.security.vo.PermissionVo;
//import com.billow.gateway.redis.RedisUtils;
//import com.billow.tools.constant.DictionaryType;
//import com.billow.tools.constant.RedisCst;
//import com.billow.tools.utlis.ToolsUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.ReactiveAuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.server.authorization.AuthorizationContext;
//import org.springframework.stereotype.Service;
//import org.springframework.util.AntPathMatcher;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * 查询用户是否有权限
// *
// * @author liuyongtao
// * @create 2019-04-29 18:11
// */
//@Service("authService")
//public class AuthService implements ReactiveAuthorizationManager<AuthorizationContext> {
//
//    protected final Logger logger = LoggerFactory.getLogger(getClass());
//
//    private AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//    private final static String PERMISSION = RedisCst.ROLE_PERMISSION_KEY;
//    private final static String DICTIONARY = RedisCst.COMM_DICTIONARY_SYS_MODULE;
//
//    @Autowired
//    private RedisUtils redisUtils;
//
////    public boolean hasPermission(ServerHttpRequest request, Authentication authentication) {
////
////        Object principal = authentication.getPrincipal();
////        logger.info("===>>> principal:{}", principal);
////
////        String targetURI = request.getURI().getPath();
////        logger.info("<<<=== targetURI:{}", targetURI);
////
////        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
////        if (authorities != null && authorities.size() > 0) {
////            // 获取 redis 中数据字典的数据
////            Map<String, String> dictionaryMap = redisUtils.getArray(DICTIONARY + DictionaryType.SYS_MC_SYSTEM_MODULE, DataDictionaryVo.class)
////                    .stream()
////                    .filter(f -> DictionaryType.SYS_FC_SYSTEM_MODULE.equals(f.getFieldType()))
////                    .collect(Collectors.toMap(DataDictionaryVo::getFieldValue, DataDictionaryVo::getFieldDisplay));
////            for (GrantedAuthority authority : authorities) {
////                // 查询 redis 中的角色权限
////                List<PermissionVo> permissionVos = redisUtils.getArray(PERMISSION + authority.getAuthority(), PermissionVo.class);
////                if (permissionVos == null) {
////                    continue;
////                }
////                for (PermissionVo permissionVo : permissionVos) {
////                    if (ToolsUtils.isEmpty(permissionVo.getSystemModule())) {
////                        String sourceURI = permissionVo.getUrl();
////                        logger.info("===>>> sourceURI:{}", sourceURI);
////                        if (antPathMatcher.match(sourceURI, targetURI)) {
////                            logger.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
////                            return true;
////                        }
////                    } else {
////                        String[] split = permissionVo.getSystemModule().split(",");
////                        for (String s : split) {
////                            String sourceURI = "/" + dictionaryMap.get(s) + permissionVo.getUrl();
////                            logger.info("===>>> sourceURI:{}", sourceURI);
////                            if (antPathMatcher.match(sourceURI, targetURI)) {
////                                logger.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
////                                return true;
////                            }
////                        }
////                    }
////                }
////            }
////            logger.info("<<<=== targetURI未获取授权:{}", targetURI);
////        }
////        return false;
////    }
//
//    @Override
//    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
//        URI uri = authorizationContext.getExchange().getRequest().getURI();
//        String targetURI = uri.getPath();
//        logger.info("<<<=== targetURI:{}", targetURI);
//        // 获取 redis 中数据字典的数据
//        Map<String, String> dictionaryMap = redisUtils.getArray(DICTIONARY + DictionaryType.SYS_MC_SYSTEM_MODULE, DataDictionaryVo.class)
//                .stream()
//                .filter(f -> DictionaryType.SYS_FC_SYSTEM_MODULE.equals(f.getFieldType()))
//                .collect(Collectors.toMap(DataDictionaryVo::getFieldValue, DataDictionaryVo::getFieldDisplay));
//
//        //认证通过且角色匹配的用户可访问当前路径
//        return mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(role -> {
//                    List<PermissionVo> permissionVos = redisUtils.getArray(PERMISSION + role, PermissionVo.class);
//                    if (permissionVos == null) {
//                        return false;
//                    }
//                    for (PermissionVo permissionVo : permissionVos) {
//                        if (ToolsUtils.isEmpty(permissionVo.getSystemModule())) {
//                            String sourceURI = permissionVo.getUrl();
//                            logger.info("===>>> sourceURI:{}", sourceURI);
//                            if (antPathMatcher.match(sourceURI, targetURI)) {
//                                logger.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
//                                return true;
//                            }
//                        } else {
//                            String[] split = permissionVo.getSystemModule().split(",");
//                            for (String s : split) {
//                                String sourceURI = "/" + dictionaryMap.get(s) + permissionVo.getUrl();
//                                logger.info("===>>> sourceURI:{}", sourceURI);
//                                if (antPathMatcher.match(sourceURI, targetURI)) {
//                                    logger.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
//                                    return true;
//                                }
//                            }
//                        }
//                    }
//                    return false;
//                })
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
//    }
//}
