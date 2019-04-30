package com.billow.auth.api;

import com.alibaba.fastjson.JSONObject;
import com.billow.auth.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

/**
 * 查询用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/15 14:32
 */
@FrameworkEndpoint
public class AuthApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

//    @GetMapping("/user")
//    public Object getCurrentUser(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
//        logger.info("authenticaiton={}", JSONObject.toJSONString(authentication));
//        String header = request.getHeader("Authorization");
//        String token = StringUtils.substringAfter(header, "bearer ");
//        logger.info("token={}", token);
//        String access_token = request.getParameter("access_token");
//        logger.info("access_token={}", access_token);
//        Claims claims = Jwts.parser()
//                .setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8"))
//                .parseClaimsJws(access_token).getBody();
//        String blog = claims.get("blog", String.class);
//        logger.info("blog={}", blog);
//
//        return authentication;
//    }

    /**
     * 认证页面
     *
     * @return ModelAndView
     */
    @GetMapping("/authentication/require")
    public ModelAndView require() {
        return new ModelAndView("/login.html");
    }

    @ResponseBody
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
