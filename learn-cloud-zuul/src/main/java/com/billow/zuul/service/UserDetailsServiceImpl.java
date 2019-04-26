package com.billow.zuul.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 查询登陆用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/20 15:19
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        logger.info("开始校验用户...");
        Set<GrantedAuthority> userAuthotities = new HashSet<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("admin");
        userAuthotities.add(simpleGrantedAuthority);
        return new User("admin", "123456", userAuthotities);
    }
}
