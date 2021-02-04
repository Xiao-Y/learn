package com.billow.mybatis.config;

import com.billow.mybatis.utils.ＭybatisUserTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean
    public ＭybatisUserTools myＭybatisUserTools(){
        return new ＭybatisUserTools();
    }
}
