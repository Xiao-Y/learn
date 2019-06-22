package com.billow.system.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author liuyongtao
 * @create 2019-04-28 17:33
 */
//@RestController
public class UserApi {

//    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
