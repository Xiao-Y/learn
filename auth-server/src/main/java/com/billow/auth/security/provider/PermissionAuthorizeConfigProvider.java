package com.billow.auth.security.provider;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 通过数据库查询权限校验
 *
 * @author liuyongtao
 * @create 2019-04-30 8:57
 */
@Component
@Order(Integer.MAX_VALUE)
public class PermissionAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // 自定义权限
        config.anyRequest()
                .access("@permissionService.hasPermission(request,authentication)");
        return true;
    }
}
