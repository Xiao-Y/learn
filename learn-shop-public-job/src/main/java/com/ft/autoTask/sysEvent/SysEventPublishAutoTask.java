package com.ft.autoTask.sysEvent;

import com.alibaba.druid.support.json.JSONUtils;
import com.ft.date.DateTime;
import com.ft.enums.SysEventEunm;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.mq.SysEventInterface;
import com.ft.sysEvent.service.SysEventPublishService;
import com.ft.utlis.BeanUtils;
import com.ft.utlis.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待发布事件
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
@EnableBinding(SysEventInterface.class)
public class SysEventPublishAutoTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysEventInterface sysEventInterface;

    public void sysEventPublish() throws Exception {

        SysEventPublishService sysEventPublishService = (SysEventPublishService) BeanUtils.getBean("sysEventPublishService");


        List<SysEventPublishDto> sysEventPublishDtos = sysEventPublishService.findByStatus(SysEventEunm.status_new.getStatusCode());
        if (ToolsUtils.isNotEmpty(sysEventPublishDtos)) {
            for (SysEventPublishDto dto : sysEventPublishDtos) {
                boolean flag = false;
                try {
                    //1.修改事件状态为已发布
                    dto.setStatus(SysEventEunm.status_published.getStatusCode());
                    sysEventPublishService.update(dto);
                    //2.发送MQ
                    String message = "系统事件：测试 " + DateTime.getSimpleDateFormat();
                    Map<String, String> jsonMap = new HashMap<>();
                    jsonMap.put("uuid", dto.getId());
                    jsonMap.put("message", message);
                    String json = JSONUtils.toJSONString(jsonMap);
                    sysEventInterface.outSysEventPublish().send(MessageBuilder.withPayload(JSONUtils.toJSONString(json)).build());
                    logger.info("【MQ发送内容】" + json);
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = true;
                } finally {
                    if (flag) {//发生了异常
                        //修改事件状态为异常
                        dto.setStatus(SysEventEunm.status_exception.getStatusCode());
                        sysEventPublishService.update(dto);
                    }
                }
            }
        }
    }
}

