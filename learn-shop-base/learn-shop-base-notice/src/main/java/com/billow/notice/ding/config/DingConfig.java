package com.billow.notice.ding.config;

import com.billow.notice.ding.properties.RobotProperties;
import com.billow.notice.ding.service.SendDingService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RobotProperties.class})
public class DingConfig {

    /**
     * 钉钉机器在人发送消息服务类
     *
     * @param
     * @return SendDingService
     * @author 千面
     * @date 2021/12/14 10:47
     */
    @Bean
    public SendDingService sendDingService() {
        return new SendDingService();
    }
}
