package com.ft.consumer;

import com.alibaba.druid.support.json.JSONUtils;
import com.ft.enums.SysEventEunm;
import com.ft.model.TestModel;
import com.ft.service.TestService;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.mq.SysEventInterface;
import com.ft.sysEvent.service.SysEventPublishService;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.Date;
import java.util.Map;

/**
 * MQ订单消费
 *
 * @author liuyongtao
 * @create 2018-02-06 17:52
 */
@EnableBinding(SysEventInterface.class)
public class TxSysEventProcessConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestService testService;
    @Autowired
    private SysEventPublishService sysEventPublishService;

    @StreamListener(SysEventInterface.IN_SYS_EVENT_PUBLISH)
    public void sysEventProcessMessage(Object message) {
        logger.info("sysEventProcessMessage=====MQ消费: " + message);
        Map<String, String> jsonMap = (Map<String, String>) JSONUtils.parse(message.toString());
        SysEventPublishDto sysEventPublishDto = sysEventPublishService.findById(jsonMap.get("uuid"));
        sysEventPublishDto.setStatus(SysEventEunm.status_process.getStatusCode());
        sysEventPublishService.update(sysEventPublishDto);
        boolean flag = false;
        try {
            TestModel test = new TestModel();
            test.setAge(22);
            test.setCreateDate(new Date());
            test.setUpdateDate(new Date());
            test.setName("billow");
            testService.saveProcess(test);
        } catch (Exception e) {
            e.printStackTrace();
            flag = true;
        } finally {
            sysEventPublishDto.setStatus(SysEventEunm.status_exception.getStatusCode());
            sysEventPublishService.update(sysEventPublishDto);
        }
        sysEventPublishDto.setStatus(SysEventEunm.status_processed.getStatusCode());
        sysEventPublishService.update(sysEventPublishDto);
    }
}
