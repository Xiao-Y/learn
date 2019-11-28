package com.billow.auth.security;

import com.alibaba.fastjson.JSONObject;
import com.billow.auth.dao.UserDao;
import com.billow.auth.pojo.po.UserPo;
import com.billow.auth.security.properties.SecurityProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityBeanConfig {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
        return converter;
    }

    @Bean
    public ClientDetailsService customClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 密码校验
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @author LiuYongTao
     * @date 2018/11/26 9:51
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密
        return new BCryptPasswordEncoder();
//        // 不加密密码
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return rawPassword.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
////                return rawPassword.equals(Md5Encrypt.md5(encodedPassword));
//                return true;
//            }
//        };
    }

    @Bean
    public DefaultTokenServices customTokenServices() {
        DefaultTokenServices tokenService = new DefaultTokenServices();
        tokenService.setTokenStore(jwtTokenStore());
        return tokenService;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * token信息扩展
     */
    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return (accessToken, authentication) -> {
            Authentication userAuthentication = authentication.getUserAuthentication();
            if (userAuthentication != null) {
                String userName = userAuthentication.getName();
                UserPo userPo = userDao.findUserInfoByUsercode(userName);
                if (userDao != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put("account", userPo.getUsername());
                    map.put("createTime", userPo.getCreateTime().toString());
                    map.put("state", String.valueOf(userPo.getValidInd()));
                    Map<String, Object> additionalInformation = new HashMap<>();
                    additionalInformation.put("user", JSONObject.toJSONString(map));
                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                }
            }
            return accessToken;
        };
    }


    @Bean
    public WebResponseExceptionTranslator loggingExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            private Log log = LogFactory.getLog(getClass());

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                //异常堆栈信息输出
                log.error("异常堆栈信息", e);
                return super.translate(e);
            }
        };
    }
}
