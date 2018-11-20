package com.billow.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 查询用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/15 14:32
 */
@RestController
public class UserApi {

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
