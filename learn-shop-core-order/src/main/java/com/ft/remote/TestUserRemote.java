package com.ft.remote;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "远程调用用户系统")
@FeignClient(value = "learn-shop-admin-user", fallback = TestUserHystric.class)
public interface TestUserRemote {

    @ApiOperation(value = "查询用户信息", tags = {"根据用户名称查询用户信息"})
    @GetMapping(value = "/testUser/indexUser")
    String indexClient(@RequestParam(value = "name") String name);
}
