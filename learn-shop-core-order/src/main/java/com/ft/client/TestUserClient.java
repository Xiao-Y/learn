package com.ft.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "learn-shop-admin-user")
@RequestMapping("/testUser")
public interface TestUserClient {

    @RequestMapping(value = "/indexUser", method = RequestMethod.GET)
    String indexClient(@RequestParam(value = "name") String name);
}
