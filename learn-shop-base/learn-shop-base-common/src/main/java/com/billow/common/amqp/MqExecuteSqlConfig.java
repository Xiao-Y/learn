package com.billow.common.amqp;

import com.billow.cloud.common.properties.ConfigCommonProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 执行sql 配置
 *
 * @author liuyongtao
 * @create 2019-10-31 10:50
 */
@Configuration
public class MqExecuteSqlConfig {

    @Autowired
    private ConfigCommonProperties configCommonProperties;

    public String getQueue() {
        return this.configCommonProperties.getMq().getQueue().getExecuteSql();
    }

    public String getExchange() {
        return this.configCommonProperties.getMq().getExchange().getExecuteSql();
    }

    public String getRouteKey() {
        return this.configCommonProperties.getMq().getRouteKey().getExecuteSql();
    }

    @Bean
    public Queue executeSqlQueue() {
        return new Queue(this.getQueue());
    }

    @Bean
    public DirectExchange executeSqlExchange() {
        return new DirectExchange(this.getExchange());
    }

    @Bean
    public Binding executeSqlBinding() {
        return BindingBuilder.bind(this.executeSqlQueue())
                .to(this.executeSqlExchange())
                .with(this.getRouteKey());
    }
}
