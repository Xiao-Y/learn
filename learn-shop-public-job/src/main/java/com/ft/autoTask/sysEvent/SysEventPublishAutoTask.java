package com.ft.autoTask.sysEvent;

import com.alibaba.druid.support.json.JSONUtils;
import com.ft.core.enumType.AutoTaskJobStatusEnum;
import com.ft.date.DateTime;
import com.ft.enums.SysEventEunm;
import com.ft.generator.UUID;
import com.ft.model.expand.ScheduleJobDto;
import com.ft.model.expand.ScheduleJobLogDto;
import com.ft.service.ScheduleJobLogService;
import com.ft.service.ScheduleJobService;
import com.ft.service.TaskManagerService;
import com.ft.sysEvent.model.expand.SysEventPublishDto;
import com.ft.sysEvent.mq.SysEventInterface;
import com.ft.sysEvent.service.SysEventPublishService;
import com.ft.utlis.BeanUtils;
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
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
@EnableBinding(SysEventInterface.class)
public class SysEventPublishAutoTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskManagerService taskManagerService;

    @Autowired
    private SysEventInterface sysEventInterface;

    @Autowired
    private SysEventPublishService sysEventPublishService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Exception exception = new Exception();

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
                    JobExecutionException ex = new JobExecutionException(e);
                    // 设置 将自动 去除 这个任务的触发器,所以这个任务不会再执行
                    ex.setUnscheduleAllTriggers(true);
                    e.printStackTrace();
                    exception = e;
                    flag = true;
                } finally {
                    if (flag) {//发生了异常
                        try {
                            //修改事件状态为异常
                            dto.setStatus(SysEventEunm.status_exception.getStatusCode());
                            sysEventPublishService.update(dto);
                            //插入异常信息，修改自动任务状态
                            taskManagerService.insertAutoTaskException(jobExecutionContext, exception);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("事务异常：" + e);
                        }
                    }
                }
            }
        }
    }
}