package com.billow.user.security.utils;

import com.billow.tools.resData.BaseResponse;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 安全工具类
 *
 * @author liuyongtao
 * @create 2018-11-19 10:58
 */
public class SecurityUtils {

    /**
     * 拼装验证不通过时的返回信息
     *
     * @param resCode 返回的代码
     * @param message 返回的消息
     * @return com.billow.tools.resData.BaseResponse
     * @author LiuYongTao
     * @date 2018/11/19 11:00
     */
    public static BaseResponse getBaseResponse(String resCode, String message) {
        BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
        baseResponse.setResCode(resCode);
        Map<String, Object> map = new HashMap<>();
        map.put("httpStatus", HttpStatus.UNAUTHORIZED);
        map.put("errorCode", HttpStatus.UNAUTHORIZED.value());
        map.put("message", message);
        baseResponse.setResData(map);
        return baseResponse;
    }
}
