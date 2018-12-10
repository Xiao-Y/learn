package com.billow.auth.security.config.enhancer;

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

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        //往jwt添加的自定义信息
        Map<String, Object> info = new HashMap<>();
//        info.put("company", "imooc");
//        info.put("product_code", "100");
//        info.put("resCode", "0000");
        info.put("blog", "https://Xiao-Y.github.io/");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
        return enhancedToken;
    }

}