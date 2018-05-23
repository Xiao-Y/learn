package com.billow.remote;

import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.vo.sys.WhiteListVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * adminSystem熔断器
 *
 * @author liuyongtao
 * @create 2018-05-23 9:35
 */
@Component
public class AdminSystemHystric implements AdminSystemRemote {

    @Override
    public BaseResponse<List<WhiteListVo>> findWhiteListVos(String ip, String module, boolean validInd) {
        BaseResponse<List<WhiteListVo>> baseResponse = new BaseResponse<>(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC.getStatusCode());
        baseResponse.setTraceID("learn-shop-admin-system");
        return baseResponse;
    }
}
