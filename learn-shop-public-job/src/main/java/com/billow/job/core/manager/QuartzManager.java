package com.billow.job.core.manager;

import com.billow.job.core.JobDataCst;
import com.billow.job.core.config.QuartzJobFactory;
import com.billow.job.core.config.QuartzJobFactoryDisallowConcurrentExecution;
import com.billow.job.core.enumType.AutoTaskJobConcurrentEnum;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.tools.utlis.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 定时任务管理器
 *
 * @author XiaoY
 * @date: 2017年5月6日 下午10:49:14
 */
@Slf4j
@Service
public class QuartzManager {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 获取所有计划中的任务列表
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @return
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:17:27
     */
    public List<ScheduleJobVo> getAllJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJobVo> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJobVo job = new ScheduleJobVo();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @return
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:17:48
     */
    public List<ScheduleJobVo> getRunningJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJobVo> jobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJobVo job = new ScheduleJobVo();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 禁用一个job
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJob JobName/JobGroup
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:17:56
     */
    public void pauseJob(ScheduleJobVo scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 启用一个job(job必须存在)
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJob JobName/JobGroup
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:18:05
     */
    public void resumeJob(ScheduleJobVo scheduleJob) throws SchedulerException {
        JobDetail jobDetail = this.getJobDetail(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        if (jobDetail == null) {
            throw new RuntimeException(scheduleJob.getJobName() + "-" + scheduleJob.getJobGroup() + ",自动任务不存在...");
        }
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        schedulerFactoryBean.getScheduler().resumeJob(jobKey);
    }

    /**
     * 删除一个job
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJob JobName/JobGroup
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:18:12
     */
    public void deleteJob(ScheduleJobVo scheduleJob) throws SchedulerException {
        String jobGroup = scheduleJob.getJobGroup();
        String jobName = scheduleJob.getJobName();
        if (ToolsUtils.isEmpty(jobName) || ToolsUtils.isEmpty(jobGroup)) {
            log.warn("JobGroup:{},JobName:{} 不能为空", jobGroup, jobName);
            return;
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行job(job必须存在)
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJob JobName/JobGroup
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:18:21
     */
    public void runAJobNow(ScheduleJobVo scheduleJob) throws SchedulerException {
        JobDetail jobDetail = this.getJobDetail(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        if (jobDetail == null) {
            throw new RuntimeException(scheduleJob.getJobName() + "-" + scheduleJob.getJobGroup() + ",自动任务不存在...");
        }
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        schedulerFactoryBean.getScheduler().triggerJob(jobKey);
    }

    /**
     * 如果存在则删除重新添加，更新job时间表达式
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJob JobName/JobGroup/CronExpression
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:18:28
     */
    public void updateJobCron(ScheduleJobVo scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 添加任务,如果存在则删除重新添加
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param job
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:18:37
     */
    public void addJob(ScheduleJobVo job) throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            Class<? extends Job> clazz;
            if (AutoTaskJobConcurrentEnum.CONCURRENT_NOT.getIsConcurrent().equals(job.getIsConcurrent())) {
                clazz = QuartzJobFactory.class;
            } else {
                clazz = QuartzJobFactoryDisallowConcurrentExecution.class;
            }
            // 指定Job在Scheduler中所属组及名称
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
            jobDetail.getJobDataMap().put(JobDataCst.SCHEDULE_JOB_VO, job);
            // 设置调度的时间规则
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            // 创建一个SimpleTrigger实例，指定该Trigger在Scheduler中所属组及名称
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
            // 注册并进行调度
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 添加任务
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param list
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:18:37
     */
    public void addJobList(List<ScheduleJobVo> list) throws Exception {
        for (ScheduleJobVo job : list) {
            this.addJob(job);
        }
    }

    /**
     * 通过jobName和jobGroup获取一个自动任务的状态
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public JobDetail getJobDetail(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        return scheduler.getJobDetail(jobKey);
    }

    /**
     * 校验 job 是否存在，如果 jobName/jobGroup 中存在为空的时候返回 true。异常时，返回true
     *
     * @param jobName
     * @param jobGroup
     * @return boolean 存在-true
     * @author LiuYongTao
     * @date 2019/8/15 11:10
     */
    public boolean isJobExist(String jobName, String jobGroup) {
        if (ToolsUtils.isEmpty(jobName) || ToolsUtils.isEmpty(jobGroup)) {
            return true;
        }
        JobDetail jobDetail;
        try {
            jobDetail = this.getJobDetail(jobName, jobGroup);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return true;
        }
        return !(jobDetail == null);
    }
}
