package com.billow.autoTask.sysEvent;

import com.billow.common.business.sysEvent.service.SysEventPublishService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 从SysEventPublish表中删除已经执行过的事物，并且记录在SysEventPublishLog表中
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
@Component
public class SysEventPublishLogAutoTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysEventPublishService sysEventPublishService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            sysEventPublishService.removeSysEventPublishToEventPublishLog();
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