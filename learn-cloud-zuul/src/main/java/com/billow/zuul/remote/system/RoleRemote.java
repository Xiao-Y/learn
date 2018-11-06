package com.billow.zuul.remote.system;

import com.billow.tools.constant.LearnSystem;
import com.billow.tools.resData.BaseResponse;
import com.billow.zuul.pojo.re.RoleRe;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 查询角色信息
 *
 * @author LiuYongTao
 * @date 2018/11/6 14:59
 */
@FeignClient(name = LearnSystem.LEARN_SHOP_ADMIN_SYSTEM, fallback = RoleHystric.class)
public interface RoleRemote {
    @GetMapping("/roleApi/findRolesInfoByUserId/{userId}")
    BaseResponse<List<RoleRe>> findRolesInfoByUserId(@PathVariable("userId") Long userId);
}
