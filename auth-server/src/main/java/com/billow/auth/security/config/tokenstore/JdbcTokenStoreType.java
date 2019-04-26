package com.billow.auth.security.config.tokenstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * JDBC 的方式
 *
 * @author liuyongtao
 * @create 2018-11-14 15:23
 */
@Component
public class JdbcTokenStoreType implements TokenStoreType {

    @Autowired
    private DataSource dataSource;

    @Override
    public TokenStore getTokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}
