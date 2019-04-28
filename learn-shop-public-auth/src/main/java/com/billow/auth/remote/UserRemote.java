package com.billow.auth.remote;

import com.billow.tools.resData.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "learn-shop-admin-user", fallback = UserHystric.class)
public interface UserRemote {

    @GetMapping("/userApi/loadUserByUsername/{userCode}")
    BaseResponse<UserDetails> loadUserByUsername(@PathVariable("userCode") String userCode);
}
