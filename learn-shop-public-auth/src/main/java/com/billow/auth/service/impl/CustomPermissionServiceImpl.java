package com.billow.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.billow.auth.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
@Service
public class CustomPermissionServiceImpl implements PermissionService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        logger.info(JSONObject.toJSONString(authentication));
        return false;
    }
}
