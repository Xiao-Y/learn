package com.billow.order.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用用户系统
 */
@FeignClient(value = "learn-shop-admin-user", fallback = TestUserHystric.class)
public interface TestUserRemote {

    /**
     * 根据用户名称查询用户信息
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/testUser/indexUser")
    String indexClient(@RequestParam(value = "name") String name);

    /**
     * 保存用户信息
     *
     * @param name
     * @return
     */
    @PostMapping("/testUser/saveUser")
    String saveUser(String name);
}
