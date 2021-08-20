package com.billow.cloud.common.properties;

import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-08-11 10:55
 */
@Data
public class ConfigCommonProperties {

    private MqProperties mq = new MqProperties();
}
