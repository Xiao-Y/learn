package com.billow.auth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationSuccessHandler customerAuthenticationSuccessRedirectHandler;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    //不定义没有password grant_type
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .permitAll()
                .successHandler(customerAuthenticationSuccessRedirectHandler)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
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
        // return new BCryptPasswordEncoder();
        // 不加密密码
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return rawPassword.equals(Md5Encrypt.md5(encodedPassword));
                return true;
            }
        };
    }
}
