package com.billow.auth.config.tokenstore;

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
@Component
public class JwtTokenStoreType implements TokenStoreType {

    @Override
    public TokenStore getTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * token 的生成器
     *
     * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
     * @author LiuYongTao
     * @date 2018/11/14 15:37
     */
    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
}
