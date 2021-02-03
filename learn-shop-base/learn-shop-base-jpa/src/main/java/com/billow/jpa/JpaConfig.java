package com.billow.jpa;

import com.billow.jpa.utils.JpaUserTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @since 2021-2-3 16:01
 */
@Configuration
public class JpaConfig {

    @Bean
    public JpaUserTools jpaUserTools() {
        return new JpaUserTools();
    }
}
