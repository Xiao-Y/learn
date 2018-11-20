package com.billow.auth.security.config.tokenstore;

import org.springframework.security.oauth2.provider.token.TokenStore;

public interface TokenStoreType {

    TokenStore getTokenStore();
}
