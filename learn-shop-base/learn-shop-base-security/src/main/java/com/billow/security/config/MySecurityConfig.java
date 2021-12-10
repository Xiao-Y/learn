package com.billow.security.config;

import com.billow.security.aspect.PreAuthorizeAspect;
import com.billow.security.util.UserTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySecurityConfig
{

    @Bean
    public UserTools userTools()
    {
        return new UserTools();
    }

    @Bean
    public PreAuthorizeAspect preAuthorizeAspect()
    {
        return new PreAuthorizeAspect();
    }
}