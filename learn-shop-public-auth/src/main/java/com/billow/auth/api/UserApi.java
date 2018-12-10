package com.billow.auth.api;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

/**
 * 查询用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/15 14:32
 */
@RestController
public class UserApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);

    @GetMapping("/user")
//    public Object getCurrentUser1(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
//        LOGGER.info("【SecurityOauth2Application】 getCurrentUser1 authenticaiton={}", JSONUtil.toJsonStr(authentication));
//        String header = request.getHeader("Authorization");
//        String token = StringUtils.substringAfter(header, "bearer ");
//
//        Claims claims = Jwts.parser().setSigningKey("123".getBytes("UTF-8")).parseClaimsJws(token).getBody();
//        String blog = (String) claims.get("blog");
//        LOGGER.info("【SecurityOauth2Application】 getCurrentUser1 blog={}", blog);
//
//        return authentication;
//    }

    public Principal user(Principal user) {
        return user;
    }
}
