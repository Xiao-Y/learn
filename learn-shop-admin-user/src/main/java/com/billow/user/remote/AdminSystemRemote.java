package com.billow.user.remote;

import com.billow.tools.resData.BaseResponse;
import com.billow.user.pojo.ex.RoleEx;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * AdminSystem 远程调用
 *
 * @author LiuYongTao
 * @date 2019/7/31 11:46
 */
@FeignClient(name = "learn-shop-admin-system", fallback = AdminSystemHystric.class)
public interface AdminSystemRemote {

    /**
     * 根据id查询角色信息
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/admin-system/roleApi/findRoleById")
    BaseResponse<List<RoleEx>> findRoleById(@RequestParam(value = "ids") List<Long> ids);
}
