package com.billow.product.producer;

import com.billow.cloud.common.properties.ConfigCommonProperties;
import com.billow.cloud.common.properties.MqProperties;
import com.billow.common.amqp.BaseMqConfig;
import com.billow.mq.StoredRabbitTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 刷新 es 缓存生产者
 *
 * @author liuyongtao
 * @create 2018-02-06 16:45
 */
@Slf4j
@Component
public class RefreshEsProducer {

    @Autowired
    private BaseMqConfig baseMqConfig;

    @Autowired
    private StoredRabbitTemplate publicRabbitTemplate;

    public void sendRefreshEsInfo() {
        String message = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        publicRabbitTemplate.messageSendMQ(baseMqConfig.getExchange().getProduct(),
                baseMqConfig.getRouteKey().getRefreshEs(),
                "99999");
        log.info("【MQ发送内容】" + message);
    }


}
