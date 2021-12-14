package com.billow.notice.config;

import com.billow.notice.amqp.yml.NoticeMqYml;
import com.billow.notice.ding.RobotProperties;
import com.billow.notice.ding.SendDingService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RobotProperties.class, NoticeMqYml.class})
public class NoticeConfig {

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
