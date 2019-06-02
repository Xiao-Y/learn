package com.billow.system.config.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
@Service("permissionService")
public class PermissionService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean isPermission = false;

        Object principal = authentication.getPrincipal();
        logger.info("===>>> principal:{}", principal);
//        if ("admin".equals(principal)) {
//            return true;
//        }

        String contextPath = request.getContextPath();
        logger.info("request.getContextPath:{}", contextPath);
        String targetURI = request.getRequestURI();
        logger.info("<<<=== targetURI:{}", targetURI);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities != null && authorities.size() > 0) {
            for (GrantedAuthority authority : authorities) {
                String sourceURI = contextPath + authority.getAuthority();
                logger.info("===>>> sourceURI:{}", sourceURI);
                if (antPathMatcher.match(sourceURI, targetURI)) {
                    isPermission = true;
                    break;
                }
            }
        }
        return isPermission;
    }
}
