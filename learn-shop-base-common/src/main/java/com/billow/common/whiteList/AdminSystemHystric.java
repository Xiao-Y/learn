package com.billow.common.whiteList;

import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.vo.sys.WhiteListVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * admin-ststem熔断器
 *
 * @author liuyongtao
 * @create 2018-05-19 14:09
 */
@Component
public class AdminSystemHystric implements AdminSystemRemote {

    @Override
    public BaseResponse<List<WhiteListVo>> findWhiteListVos(String ip, String module, boolean validInd) {
        BaseResponse<List<WhiteListVo>> baseResponse = BaseResponse.newBaseResponse();
        baseResponse.setResCode(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC.getStatusCode());
        return baseResponse;
    }
}
