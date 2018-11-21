package com.billow.auth.security.translator;

import com.billow.auth.security.exception.TokenOauthException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * token 验证不通过的响应异常信息
 *
 * @author liuyongtao
 * @create 2018-11-20 16:56
 */
@Component
public class TokenOauthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        // Try to extract a SpringSecurityException from the stacktrace
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

        // 异常栈获取 OAuth2Exception 异常
        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);

        // 异常栈中有OAuth2Exception
        if (ase != null) {
            return handleOAuth2Exception((OAuth2Exception) ase);
        }

        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new UnauthorizedUserException(e.getMessage(), e));
        }

//        ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
//        if (ase instanceof AccessDeniedException) {
//            return handleOAuth2Exception(new ForbiddenException(ase.getMessage()));
//        }

        // 不包含上述异常则服务器内部错误
        return handleOAuth2Exception(new OAuth2Exception(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {

        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }

        TokenOauthException exception = new TokenOauthException(e.getMessage(), e);

        ResponseEntity<OAuth2Exception> response = new ResponseEntity<>(exception, headers, HttpStatus.valueOf(status));

        return response;

    }
}
