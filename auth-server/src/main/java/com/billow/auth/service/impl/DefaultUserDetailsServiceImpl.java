package com.billow.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 查询用户信息。默认不实现,用于提示用户自己实现
 *
 * @author LiuYongTao
 * @date 2019/4/29 18:05
 */
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.warn("请配置 UserDetailsService 接口的实现.");
        throw new UsernameNotFoundException(username);
    }
}
