package com.billow.gateway.security.api;

import com.billow.gateway.security.util.LoginUserHolder;
import com.billow.gateway.security.vo.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongtao
 * @since 2020-12-17 9:41
 */
@RestController
@RequestMapping("/api/userApi")
public class UserApi {

    @Autowired
    private LoginUserHolder loginUserHolder;

    @GetMapping("/currentUser")
    public UserDto currentUser(ServerHttpRequest request) {
        return loginUserHolder.getCurrentUser(request);
    }
}
