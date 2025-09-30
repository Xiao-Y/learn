package com.billow.gateway.security.api;

import cn.hutool.core.util.StrUtil;
import com.billow.gateway.pojo.search.UserSearchParam;
import com.billow.gateway.pojo.vo.UserRelationVo;
import com.billow.gateway.security.service.CustomUserDetailsService;
import com.billow.gateway.security.util.JwtTokenUtil;
import com.billow.gateway.security.vo.TokenVo;
import com.billow.gateway.security.vo.UserVo;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/currentUser")
    public UserVo getCurrentUser(ServerHttpRequest request) {
        UserVo userVo = new UserVo();
        //从Header中获取用户token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("Authorization");
        log.info("获取到的Authorization:{}", token);
        if (StrUtil.isEmpty(token)) {
            return userVo;
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            return jwtTokenUtil.getUserVoFromToken(realToken);
        } catch (Exception e) {
            log.error("获取用户异常:{}", e);
        }
        return userVo;
    }

    /**
     * 用于前后分离时登陆
     *
     * @param userVo
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserSearchParam userVo) {

        BaseResponse baseResponse = new BaseResponse();

        try {
            String usercode = userVo.getUsername();
            String password = userVo.getPassword();
            log.info("Username:{},Password:{}", usercode, password);
            Assert.notNull(usercode, "用户名不能为空!");
            Assert.notNull(password, "密码不能为空!");
            // 鉴权
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usercode, password));
            // 查询用户信息
            UserRelationVo userRelationVo = customUserDetailsService.queryUserRelationByUsercode(usercode);
            // 生成token
            String accessToken = jwtTokenUtil.generateToken(userRelationVo);
            log.info("accessToken:{}", accessToken);
            log.info("refreshToken:{}", accessToken);
            TokenVo tokenVo = new TokenVo();
            tokenVo.setAccessToken(accessToken);
            tokenVo.setRefreshToken(accessToken);
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
//            TokenVo tokenVo = oauth2Holder.getTokenByRefreshToken(refreshToken);
//            log.info("accessToken:{}", tokenVo.getAccessToken());
//            log.info("refreshToken:{}", tokenVo.getRefreshToken());
//            baseResponse.setResData(tokenVo);
//            baseResponse.setResCode(ResCodeEnum.RESCODE_ASSESS_TOKEN);
        } catch (Exception e) {
            log.error("登陆异常：{}", e);
            baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER);
        }
        return baseResponse;
    }
}
