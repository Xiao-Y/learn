package com.billow.remote;

import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.re.WhiteListRe;
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
    public BaseResponse<List<WhiteListRe>> findWhiteListVos(String ip, String module, boolean validInd) {
        BaseResponse<List<WhiteListRe>> baseResponse = new BaseResponse<>(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC.getStatusCode());
        baseResponse.setTraceID("learn-shop-admin-system");
        return baseResponse;
    }
}
