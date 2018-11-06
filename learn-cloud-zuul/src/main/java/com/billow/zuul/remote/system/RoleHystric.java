package com.billow.zuul.remote.system;

import com.billow.tools.constant.LearnSystem;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.billow.zuul.pojo.re.RoleRe;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色信息断路器
 *
 * @author liuyongtao
 * @create 2018-11-06 14:59
 */
@Component
public class RoleHystric implements RoleRemote {
    @Override
    public BaseResponse<List<RoleRe>> findRolesInfoByUserId(Long userId) {
        BaseResponse<List<RoleRe>> baseResponse = new BaseResponse<>(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC.getStatusCode());
        baseResponse.setTraceID(LearnSystem.LEARN_SHOP_ADMIN_SYSTEM);
        return baseResponse;
    }
}
