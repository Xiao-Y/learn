package com.billow.mq.properties;

import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-09-30 15:53
 */
@Data
public class CustomProperties {

    private Boolean isRetry = true;
    private Integer sendRetryCount = 3;
    private Integer receiveRetryCount = 3;
    private String retryCron = "0/5 * * * * ?";
    private Integer cacheThreshold = 100;
    private String templateName = "storedRabbitTemplate";
}
