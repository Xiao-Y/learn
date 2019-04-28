//package com.billow.auth.security.config;
//
//import com.billow.auth.properties.OAuth2Properties;
//import com.billow.auth.security.config.enhancer.CustomJwtAccessTokenConverter;
//import com.billow.auth.security.endpoint.TokenEmptyEntryPoint;
//import com.billow.auth.security.handler.TokenAccessDeniedHandler;
//import com.billow.auth.security.translator.TokenOauthWebResponseExceptionTranslator;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Autowired
//    private TokenEmptyEntryPoint tokenEmptyEntryPoint;
//    @Autowired
//    private TokenAccessDeniedHandler tokenAccessDeniedHandler;
//    @Autowired
//    private TokenOauthWebResponseExceptionTranslator tokenOauthWebResponseExceptionTranslator;
//    @Autowired
//    private DataSource dataSource;
//    @Autowired
//    private OAuth2Properties oAuth2Properties;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers(antMatchersPermitAll()).permitAll()
//                .anyRequest().authenticated()
//                // 异常处理
//                .and().exceptionHandling()
//                // 不传令牌，令牌错误（失效）等
//                .authenticationEntryPoint(tokenEmptyEntryPoint)
//                // 令牌不能访问该资源 （403）异常等
//                .accessDeniedHandler(tokenAccessDeniedHandler)
//                .and().httpBasic();
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        // 定义异常转换类生效
//        OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
//        authenticationEntryPoint.setExceptionTranslator(tokenOauthWebResponseExceptionTranslator);
//        resources
//                .tokenStore(tokenStore())
//                .authenticationEntryPoint(authenticationEntryPoint);
//    }
//
//    @Bean
//    @Primary
//    public ClientDetailsService clientDetails() {
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//    @Bean
//    @Primary
//    public TokenStore tokenStore() {
//        // token存储,这里使用jwt方式存储
//        TokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
//        return tokenStore;
//    }
//
//    @Bean
//    @Primary
//    public JwtAccessTokenConverter accessTokenConverter() {
//        // Token转换器必须与认证服务一致
//        CustomJwtAccessTokenConverter accessTokenConverter = new CustomJwtAccessTokenConverter();
//        // 测试用,授权服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
//        accessTokenConverter.setSigningKey(oAuth2Properties.getJwtSigningKey());
//        return accessTokenConverter;
//    }
//
//    /**
//     * 不需要认证的
//     *
//     * @return java.lang.String[]
//     * @author LiuYongTao
//     * @date 2019/4/28 10:22
//     */
//    private String[] antMatchersPermitAll() {
//        List<String> all = new ArrayList<>();
//        // swagger start
//        all.add("/swagger-ui.html");
//        all.add("/swagger-resources/**");
//        all.add("/images/**");
//        all.add("/webjars/**");
//        all.add("/v2/api-docs");
//        all.add("/configuration/ui");
//        all.add("/configuration/security");
//        // swagger end
//        String[] array = all.toArray(new String[all.size()]);
//        return array;
//    }
//}
