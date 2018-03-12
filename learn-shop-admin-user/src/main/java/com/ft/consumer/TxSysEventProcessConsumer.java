package com.ft.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ft.enums.SysEventEunm;
import com.ft.model.TestModel;
import com.ft.service.TestService;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.mq.SysEventInterface;
import com.ft.sysEvent.service.SysEventPublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

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
    public void sysEventProcessMessage(String message) {
        logger.info("sysEventProcessMessage=====MQ消费: " + message);
        boolean flag = false;
        //消费
        Map<String, String> jsonMap = JSON.parseObject(message, Map.class);
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
            SysEventPublishDto sysEventPublishDto = sysEventPublishService.findById(jsonMap.get("uuid"));
            if (flag) {
                sysEventPublishDto.setStatus(SysEventEunm.status_pro_exception.getStatusCode());
            } else {
                sysEventPublishDto.setStatus(SysEventEunm.status_processed.getStatusCode());
            }
            sysEventPublishService.update(sysEventPublishDto);
        }
    }
}
