package com.billow.system.feign;

import com.billow.system.pojo.ex.UserEx;
import com.billow.tools.resData.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AdminUser 远程调用
 *
 * @author billow
 * @date 2019/9/1 13:16
 */
@FeignClient(name = "learn-shop-admin-user", fallback = AdminUserHystric.class)
public interface AdminUserFeign {

    /**
     * 根据 id 查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/admin-user/userApi/findUserInfoById")
    BaseResponse<UserEx> findUserInfoById(@RequestParam(value = "id") Long id);

    /**
     * 根据 userCode 查询用户信息
     *
     * @param userCode
     * @return
     */
    @GetMapping(value = "/admin-user/userApi/findUserInfoByUserCode")
    BaseResponse<UserEx> findUserInfoByUserCode(@RequestParam(value = "userCode") String userCode);
}
