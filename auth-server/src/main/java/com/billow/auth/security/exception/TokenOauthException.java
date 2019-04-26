package com.billow.auth.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = TokenOauthExceptionSerializer.class)
public class TokenOauthException extends OAuth2Exception {

    public TokenOauthException(String msg) {
        super(msg);
    }

    public TokenOauthException(String msg, Throwable t) {
        super(msg, t);
    }
}