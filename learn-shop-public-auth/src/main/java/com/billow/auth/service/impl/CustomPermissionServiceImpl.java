package com.billow.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.billow.auth.pojo.security.CustomUserDetails;
import com.billow.auth.pojo.security.Permission;
import com.billow.auth.service.PermissionService;
import com.billow.tools.utlis.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
@Service("permissionService")
public class CustomPermissionServiceImpl implements PermissionService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        logger.info("learn-shop-public-auth---->hasPermission:{}", JSONObject.toJSONString(authentication));
        boolean isPermission = false;
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            List<Permission> permissions = ((CustomUserDetails) principal).getPermissions();
            if (ToolsUtils.isNotEmpty(permissions)) {
                for (Permission permission : permissions) {
                    if (antPathMatcher.match(permission.getUrl(), request.getRequestURI())) {
                        isPermission = true;
                        break;
                    }
                }
            }
        }
        return true;
    }
}
