package com.billow.mq.retry;

import com.billow.mq.properties.CustomProperties;
import com.billow.mq.properties.MqProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 默认实现下次重试测试
 *
 * @author liuyongtao
 * @create 2019-10-18 15:22
 */
public class NextRetryDateDefault implements NextRetryDate {

    @Autowired
    private MqProperties mqProperties;

    @Override
    public Date nextRetryDate(long tryCount) {
        CustomProperties custom = mqProperties.getCustom();
        long retryFactor = custom.getSendRetryFactor();
        long def = 10000 + (tryCount * retryFactor * 1000);
        Date date = new Date(new Date().getTime() + def);
        return date;
    }
}
