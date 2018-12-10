package com.billow.auth.security.config.tokenstore;

import com.billow.auth.security.config.enhancer.CustomJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

/**
 * JWT 形式的token,不保存
 *
 * @author liuyongtao
 * @create 2018-11-14 15:34
 */
public class JwtTokenStoreType{

    @Autowired
    private SecurityProperties securityProperties;

    public TokenStore getTokenStore() {
        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    /**
     * token 的生成器
     *
     * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
     * @author LiuYongTao
     * @date 2018/11/14 15:37
     */
//    @Override
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        CustomJwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
//        securityProperties.getOauth2().getJetSigningKey()
        converter.setSigningKey("123");
        return converter;
    }
}
