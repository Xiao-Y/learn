package com.billow.job.core.config;

import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.util.TaskUtils;
import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 计划任务执行处 无状态(相当于多线程的)
 *
 * @author XiaoY
 * @date: 2017年5月6日 下午10:45:29
 */
@PersistJobDataAfterExecution
public class QuartzJobFactory implements Job {

    public final Logger log = Logger.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            ScheduleJobVo scheduleJob = (ScheduleJobVo) context.getMergedJobDataMap().get("scheduleJob");
            TaskUtils.invokMethod(scheduleJob);
        } catch (Exception e) {
            JobExecutionException ex = new JobExecutionException(e);
            // 设置 将自动 去除 这个任务的触发器,所以这个任务不会再执行
            ex.setUnscheduleAllTriggers(true);
            // 抛出异常
            throw ex;
        }
    }
}
