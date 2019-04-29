package com.billow.auth.service.impl;

import com.billow.auth.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询用户是否有权限。默认不实现,用于提示用户自己实现
 *
 * @author liuyongtao
 * @create 2019-04-29 18:03
 */
public class DefaultPermissionServiceImpl implements PermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        logger.warn("请配置 PermissionService 接口的实现.");
        throw new UnsupportedOperationException();
    }
}
