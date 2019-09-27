package com.billow.job.core.config;

import com.billow.job.constant.JobCst;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.util.DateUtils;
import com.billow.job.util.TaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * 监控job 的执行
 *
 * @author liuyongtao
 * @create 2019-08-14 10:53
 */
@Slf4j
public class MonitorJobListener implements JobListener {

    public static final String LISTENER_NAME = "JobListenerName";

    @Override
    public String getName() {
        return LISTENER_NAME;
    }

    // 定时任务开始执行
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        ScheduleJobVo scheduleJobVo = (ScheduleJobVo) jobDataMap.get(JobCst.SCHEDULE_JOB_VO);
        log.info("===========>>>> Job : {} is going to start...", scheduleJobVo.getJobName());
        log.info("Job param:{}", scheduleJobVo.toString());
    }

    // 执行被否决
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        ScheduleJobVo scheduleJobVo = (ScheduleJobVo) jobDataMap.get(JobCst.SCHEDULE_JOB_VO);
        log.warn("Job : {} is is Execution Vetoed...", scheduleJobVo.getJobName());
    }

    // 定时任务执行完毕
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exception) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        ScheduleJobVo scheduleJob = (ScheduleJobVo) jobDataMap.get(JobCst.SCHEDULE_JOB_VO);
        String jobRunTime = DateUtils.covertLongToString(context.getJobRunTime());
        scheduleJob.setRunTime(jobRunTime);
        // 插入执行日志（发送邮件mq）
        TaskUtils.saveLog(scheduleJob, exception);
        log.info("<<<<=========== Job : {},RunTime : {} is finished...", scheduleJob.getJobName(), jobRunTime);
    }
}
