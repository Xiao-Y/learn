package com.billow.job.core.config;

import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.util.TaskUtils;
import org.apache.log4j.Logger;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作(相当于单线程的)
 *
 * @author liuyongtao
 * @date 2017年5月7日 下午5:22:03
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {

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