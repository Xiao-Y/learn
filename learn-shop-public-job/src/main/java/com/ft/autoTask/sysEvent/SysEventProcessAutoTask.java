package com.ft.autoTask.sysEvent;

import com.ft.enums.SysEventEunm;
import com.ft.service.TaskManagerService;
import com.ft.sysEvent.model.expand.SysEventProcessDto;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.mq.SysEventInterface;
import com.ft.sysEvent.service.SysEventProcessService;
import com.ft.sysEvent.service.SysEventPublishService;
import com.ft.utlis.ToolsUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 查询出所有发送mq异常的，重新发送，如果再次失败,则标记为失败
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
@Component
@EnableBinding(SysEventInterface.class)
public class SysEventProcessAutoTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysEventInterface sysEventInterface;

    @Autowired
    private SysEventPublishService sysEventPublishService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<SysEventPublishDto> sysEventPublishDtos = sysEventPublishService.findByStatus(SysEventEunm.status_pub_exception.getStatusCode());
        if (ToolsUtils.isNotEmpty(sysEventPublishDtos)) {
            for (SysEventPublishDto dto : sysEventPublishDtos) {
                boolean flag = false;
                try {
                    //2.发送MQ
                    sysEventInterface.outSysEventPublish().send(MessageBuilder.withPayload(dto.getPayload()).build());
                    logger.info("【MQ重新发送内容】" + dto.getPayload());
                    dto.setStatus(SysEventEunm.status_published.getStatusCode());
                    sysEventPublishService.update(dto);
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = true;
                } finally {
                    if (flag) {//发生了异常
                        //修改事件状态为异常
                        dto.setStatus(SysEventEunm.status_fail.getStatusCode());
                        sysEventPublishService.update(dto);
                    }
                }
            }
        }
    }
}
