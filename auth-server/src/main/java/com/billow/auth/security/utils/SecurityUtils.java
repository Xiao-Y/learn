package com.billow.auth.security.utils;

import com.billow.auth.supper.BaseResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

public final class SecurityUtils {

    /**
     * 获取当前登陆的用户信息
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2018/11/19 11:26
     */
    public static String getCurrentUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();

        }
        return currentUserName;
    }

    /**
     * 拼装验证不通过时的返回信息
     *
     * @param resCode    返回的代码
     * @param httpStatus 错误状态码
     * @param errorCode  错误状态说明
     * @param message    返回的消息
     * @return com.billow.tools.resData.BaseResponse
     * @author LiuYongTao
     * @date 2018/11/19 11:00
     */
    public static BaseResponse getBaseResponse(String resCode, int httpStatus, String errorCode, String message) {
        BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
        baseResponse.setResCode(resCode);
        Map<String, Object> map = new HashMap<>();
        map.put("httpStatus", httpStatus);
        map.put("errorCode", errorCode);
        map.put("message", message);
        baseResponse.setResData(map);
        return baseResponse;
    }
}
