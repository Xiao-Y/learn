package com.billow.zuul.remote.user;

import com.billow.tools.constant.LearnSystem;
import com.billow.tools.resData.BaseResponse;
import com.billow.zuul.pojo.re.UserRe;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程调用User 系统的UserApi
 *
 * @author liuyongtao
 * @create 2018-11-06 9:26
 */
@FeignClient(name = LearnSystem.LEARN_SHOP_ADMIN_USER, fallback = UserHystric.class)
public interface UserRemote {

    @GetMapping("/userApi/findUserInfoByUsercode/{userCode}")
    BaseResponse<UserRe> findUserInfoByUsercode(@PathVariable("userCode") String userCode);
}
