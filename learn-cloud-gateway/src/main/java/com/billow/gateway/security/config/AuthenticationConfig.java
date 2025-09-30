package com.billow.gateway.security.config;

import com.billow.gateway.security.service.CustomUserDetailsService;
import com.billow.gateway.security.properties.AuthProperties;
import com.billow.gateway.security.properties.TokenProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

/**
 * 认证管理器
 *
 * @author Billow
 * @date 2021/12/23
 */
@Configuration
public class AuthenticationConfig {

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new CustomReactiveAuthenticationManager();
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }


    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证提供者
     *
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //设置获取用户信息的bean
        authProvider.setUserDetailsService(userDetailsService());
        //设置密码加密器
        authProvider.setPasswordEncoder(passwordEncoder());
        ProviderManager pm = new ProviderManager(authProvider);
        return pm;
    }

    @Bean
    public KeyPair keyPair(AuthProperties authProperties) {
        TokenProperties token = authProperties.getToken();
        String jwtFileName = token.getJwtFileName();
        String jwtPassword = token.getJwtPassword();
        String alias = token.getAlias();

        try {
            // 从classpath加载keystore
            ClassPathResource resource = new ClassPathResource(jwtFileName);
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(resource.getInputStream(), jwtPassword.toCharArray());

            // 获取私钥
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, jwtPassword.toCharArray());

            // 获取公钥
            Certificate certificate = keyStore.getCertificate(alias);
            PublicKey publicKey = certificate.getPublicKey();

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load key pair from keystore", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }
}