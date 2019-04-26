package com.billow.auth.security.config.tokenstore;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

/**
 * 保存在redis中
 *
 * @author liuyongtao
 * @create 2018-11-14 15:32
 */
//@Component
public class RedisTokenStoreType implements TokenStoreType {
    @Override
    public TokenStore getTokenStore() {
        return null;
    }

//    @Autowired
//    private RedisConnectionFactory connectionFactory;
//
//    @Override
//    public TokenStore getTokenStore() {
//        return new RedisTokenStore(connectionFactory);
//    }
}
