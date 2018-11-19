package com.billow.auth.security;

import com.billow.tools.resData.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public final class SecurityUtils {

    /**
     * 获取当前登陆的用户信息
     *
     * @param []
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
