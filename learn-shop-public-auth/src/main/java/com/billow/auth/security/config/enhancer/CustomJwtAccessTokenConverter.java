package com.billow.auth.security.config.enhancer;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt增强器
 *
 * @author liuyongtao
 * @create 2018-12-04 17:17
 */
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private static final Logger logger = LoggerFactory.getLogger(CustomJwtAccessTokenConverter.class);

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken enhancedToken = (DefaultOAuth2AccessToken) super.enhance(accessToken, authentication);
        logger.info(JSONObject.toJSONString(enhancedToken, true));
        //往jwt添加的自定义信息
        Map<String, Object> info = new HashMap<>();
        info.put("resCode", "1111");
        info.put("github", "https://Xiao-Y.github.io/");
        enhancedToken.setAdditionalInformation(info);
        return enhancedToken;
    }

}