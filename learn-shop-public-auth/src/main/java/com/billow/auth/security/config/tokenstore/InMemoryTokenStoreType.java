package com.billow.auth.security.config.tokenstore;

import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.stereotype.Component;

/**
 * 保存在内存里面
 *
 * @author liuyongtao
 * @create 2018-11-14 15:31
 */
@Component
public class InMemoryTokenStoreType implements TokenStoreType {

    @Override
    public TokenStore getTokenStore() {
        return new InMemoryTokenStore();
    }
}
