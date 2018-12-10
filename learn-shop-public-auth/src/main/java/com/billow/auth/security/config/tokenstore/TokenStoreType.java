package com.billow.auth.security.config.tokenstore;

import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public interface TokenStoreType {

    TokenStore getTokenStore();

//    /**
//     * token 的生成器
//     *
//     * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
//     * @author LiuYongTao
//     * @date 2018/11/14 15:37
//     */
//    default JwtAccessTokenConverter jwtAccessTokenConverter() {
//        return null;
//    }
}
