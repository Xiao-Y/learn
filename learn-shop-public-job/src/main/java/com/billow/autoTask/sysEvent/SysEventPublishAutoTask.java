package com.billow.autoTask.sysEvent;

import com.alibaba.fastjson.JSON;
import com.billow.common.amqp.RabbitMqConfig;
import com.billow.common.enums.SysEventEunm;
import com.billow.common.sysEvent.model.expand.SysEventPublishDto;
import com.billow.common.sysEvent.service.SysEventPublishService;
import com.billow.service.TaskManagerService;
import com.billow.tools.date.DateTime;
import com.billow.tools.utlis.ToolsUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待发布事件
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
@Component
public class SysEventPublishAutoTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskManagerService taskManagerService;

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    @Autowired
    private SysEventPublishService sysEventPublishService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Exception exception = new Exception();

        List<SysEventPublishDto> sysEventPublishDtos = sysEventPublishService.findByStatus(SysEventEunm.status_publish.getStatusCode());
        if (ToolsUtils.isNotEmpty(sysEventPublishDtos)) {
            for (SysEventPublishDto dto : sysEventPublishDtos) {
                boolean flag = false;
                try {
                    //0.将要发送mq的内容
                    String message = "系统事件：测试 " + DateTime.getSimpleDateFormat();
                    Map<String, String> jsonMap = new HashMap<>();
                    jsonMap.put("uuid", dto.getId());
                    jsonMap.put("message", message);
                    jsonMap.put("sysEventPublish", JSON.toJSONString(dto));
                    String json = JSON.toJSONString(jsonMap);

                    //1.修改事件状态为已发布
                    dto.setStatus(SysEventEunm.status_published.getStatusCode());
                    dto.setPayload(json);
                    sysEventPublishService.update(dto);

                    //2.发送MQ
                    amqpTemplate.convertAndSend(rabbitMqConfig.getSysEventPublishQueue().getName(), json);
//                    sysEventInterface.outSysEventPublish().send(MessageBuilder.withPayload(json).build());
                    logger.info("【MQ发送内容】" + json);
                } catch (Exception e) {
                    e.printStackTrace();
                    exception = e;
                    flag = true;
                } finally {
                    if (flag) {//发生了异常
                        try {
                            //修改事件状态为异常
                            dto.setStatus(SysEventEunm.status_pub_exception.getStatusCode());
                            sysEventPublishService.update(dto);
                            //插入异常信息，修改自动任务状态
                            taskManagerService.insertAutoTaskException(jobExecutionContext, exception);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("事务异常：" + e);
                            JobExecutionException ex = new JobExecutionException(e);
                            // 设置 将自动 去除 这个任务的触发器,所以这个任务不会再执行
                            ex.setUnscheduleAllTriggers(true);
                            throw ex;
                        }
                    }
                }
            }
        }
    }
}