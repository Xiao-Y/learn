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
        ex.setGroupId("deptLeader");
        ex.setId(2L);
        return base;
    }

    @Override
    public BaseResponse<UserEx> findUserInfoByUserCode(String userCode) {
        BaseResponse<UserEx> base = new BaseResponse(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC);
        UserEx ex = new UserEx();
        ex.setUsercode(userCode);
        if (userCode.equals("admin")) {
            ex.setGroupId("deptLeader");
            ex.setId(2L);
            base.setResCode(ResCodeEnum.OK);
        } else if (userCode.equals("liuyongtao")) {
            ex.setGroupId("hr");
            ex.setId(1L);
            base.setResCode(ResCodeEnum.OK);
        } else if (userCode.equals("hr")) {
            ex.setGroupId("hr");
            ex.setId(3L);
            base.setResCode(ResCodeEnum.OK);
        }
        base.setResData(ex);
        return base;
    }
}
