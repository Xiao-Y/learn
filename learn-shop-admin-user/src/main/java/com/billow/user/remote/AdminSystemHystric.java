package com.billow.user.remote;

import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.billow.user.pojo.ex.RoleEx;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AdminSystem 熔断器
 *
 * @author LiuYongTao
 * @date 2019/7/31 11:46
 */
@Component
public class AdminSystemHystric implements AdminSystemRemote {

    @Override
    public BaseResponse<List<RoleEx>> findRoleById(List<Long> ids) {
        BaseResponse<List<RoleEx>> base = new BaseResponse(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC);
        return base;
    }
}
