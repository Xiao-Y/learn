package com.billow.gateway.security.api;

import com.billow.gateway.security.util.Oauth2Holder;
import com.billow.gateway.security.vo.TokenVo;
import com.billow.gateway.security.vo.UserVo;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyongtao
 * @since 2020-12-17 9:41
 */
@Slf4j
@RestController
@RequestMapping("/userApi")
public class UserApi {
    @Autowired
    private Oauth2Holder oauth2Holder;

    @GetMapping("/currentUser")
    public UserVo currentUser(ServerHttpRequest request) {
        return oauth2Holder.getCurrentUser(request);
    }

    /**
     * 用于前后分离时登陆
     *
     * @param userVo
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserVo userVo) {

        BaseResponse baseResponse = new BaseResponse();

        try {
            String username = userVo.getUsername();
            String password = userVo.getPassword();
            log.info("Username:{},Password:{}", username, password);
            Assert.notNull(username, "用户名不能为空!");
            Assert.notNull(password, "密码不能为空!");
            TokenVo tokenVo = oauth2Holder.getTokenByUsernameAndPassword(username, password);
            log.info("accessToken:{}", tokenVo.getAccessToken());
            log.info("refreshToken:{}", tokenVo.getRefreshToken());
            baseResponse.setResData(tokenVo);
            baseResponse.setResCode(ResCodeEnum.RESCODE_ASSESS_TOKEN);
        } catch (Exception e) {
            log.error("登陆异常：{}", e);
            baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER);
        }
        return baseResponse;
    }

    @ResponseBody
    @PostMapping("/refreshToken")
    public BaseResponse refreshToken(@RequestParam("refreshToken") String refreshToken) {

        BaseResponse baseResponse = new BaseResponse();

        try {
            TokenVo tokenVo = oauth2Holder.getTokenByRefreshToken(refreshToken);
            log.info("accessToken:{}", tokenVo.getAccessToken());
            log.info("refreshToken:{}", tokenVo.getRefreshToken());
            baseResponse.setResData(tokenVo);
            baseResponse.setResCode(ResCodeEnum.RESCODE_ASSESS_TOKEN);
        } catch (Exception e) {
            log.error("登陆异常：{}", e);
            baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER);
        }
        return baseResponse;
    }
}
