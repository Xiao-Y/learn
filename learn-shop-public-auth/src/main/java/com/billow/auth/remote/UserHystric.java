package com.billow.auth.remote;

//import com.billow.tools.enums.ResCodeEnum;
//import com.billow.tools.resData.BaseResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 用户服务熔断器
 *
 * @author liuyongtao
 * @create 2019-04-28 9:37
 */
@Component
public class UserHystric implements UserRemote {

//    @Override
//    public BaseResponse<UserDetails> loadUserByUsername(String userCode) {
//        BaseResponse<UserDetails> baseResponse = new BaseResponse<>(ResCodeEnum.RESCODE_SYSTEM_HYSTRIC.getStatusCode());
//        baseResponse.setTraceID("learn-shop-admin-user");
//        System.out.println("learn-shop-admin-user========>请求熔断....");
//        return baseResponse;
//    }
}
