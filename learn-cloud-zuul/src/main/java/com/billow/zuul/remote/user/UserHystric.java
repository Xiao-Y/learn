package com.billow.zuul.remote.user;

import com.billow.tools.constant.LearnSystem;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.billow.zuul.pojo.re.UserRe;

import org.springframework.stereotype.Component;

/**
 * 远程调用User 系统的UserApi熔断器
 *
 * @author liuyongtao
 * @create 2018-11-06 9:28
 */
@Component
public class UserHystric implements UserRemote {
    @Override
    public BaseResponse<UserRe> findUserInfoByUsercode(String userCode) {
        BaseResponse<UserRe> baseResponse = new BaseResponse<>(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC.getStatusCode());
        baseResponse.setTraceID(LearnSystem.LEARN_SHOP_ADMIN_USER);
        return baseResponse;
    }
}
