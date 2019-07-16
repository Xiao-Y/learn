package com.billow.system.config.security;


import com.billow.common.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
@Service("authService")
public class AuthService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 判断是否有权限
     *
     * @param request
     * @param authentication
     * @return boolean
     * @author LiuYongTao
     * @date 2019/7/16 14:30
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean isPermission = false;

        Object principal = authentication.getPrincipal();
        logger.info("===>>> principal:{}", principal);

        String contextPath = request.getContextPath();
        logger.info("request.getContextPath:{}", contextPath);
        String targetURI = request.getRequestURI();
        logger.info("<<<=== targetURI:{}", targetURI);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities != null && authorities.size() > 0) {
            for (GrantedAuthority authority : authorities) {
                // 查询 redis 中的角色权限
                List<PermissionVo> permissionVos = redisUtils.getArray("ROLE:" + authority.getAuthority(), PermissionVo.class);
                for (PermissionVo permissionVo : permissionVos) {
                    String sourceURI = contextPath + permissionVo.getUrl();
                    logger.info("===>>> sourceURI:{}", sourceURI);
                    if (antPathMatcher.match(sourceURI, targetURI)) {
                        isPermission = true;
                        break;
                    }
                }
            }
        }
        return isPermission;
    }
}
