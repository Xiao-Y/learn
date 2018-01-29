package com.ft.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "learn-shop-admin-user", fallback = TestUserHystric.class)
public interface TestUserRemote {

    @RequestMapping(value = "/testUser/indexUser", method = RequestMethod.GET)
    String indexClient(@RequestParam(value = "name") String name);
}
