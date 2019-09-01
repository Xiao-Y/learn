package com.billow.system.feign;

import com.billow.system.pojo.ex.UserEx;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import org.springframework.stereotype.Component;

/**
 * AdminSystem 熔断器
 *
 * @author LiuYongTao
 * @date 2019/7/31 11:46
 */
@Component
public class AdminUserHystric implements AdminUserFeign {

    @Override
    public BaseResponse<UserEx> findUserInfoById(Long id) {
        BaseResponse<UserEx> base = new BaseResponse(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC);
        UserEx ex = new UserEx();
        ex.setUsercode("admin");
        ex.setId(2L);
        return base;
    }
}
