package com.billow.job.core.manager;

import com.billow.job.constant.JobCst;
import com.billow.job.core.config.QuartzJobFactory;
import com.billow.job.core.config.QuartzJobFactoryDisallowConcurrentExecution;
import com.billow.job.core.enumType.AutoTaskJobConcurrentEnum;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.util.ToolsUtils;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 定时任务管理器API
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
     * 停止一个job
     *
     * @param scheduleJob 必要参数：JobName，JobGroup
     * @return void
     * @author LiuYongTao
     * @date 2019/8/16 11:10
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pauseJob(ScheduleJobVo scheduleJob) throws SchedulerException {
        String jobGroup = scheduleJob.getJobGroup();
        String jobName = scheduleJob.getJobName();
        if (!this.checkJob(jobName, jobGroup)) {
            return;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 启用一个job
     *
     * @param scheduleJob 必要参数：JobName，JobGroup
     * @return void
     * @author LiuYongTao
     * @date 2019/8/16 11:11
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void resumeJob(ScheduleJobVo scheduleJob) throws SchedulerException {
        String jobGroup = scheduleJob.getJobGroup();
        String jobName = scheduleJob.getJobName();
        if (!this.checkJob(jobName, jobGroup)) {
            return;
        }

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        schedulerFactoryBean.getScheduler().resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob 必要参数：JobName，JobGroup
     * @return void
     * @author LiuYongTao
     * @date 2019/8/16 11:11
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteJob(ScheduleJobVo scheduleJob) throws SchedulerException {
        String jobGroup = scheduleJob.getJobGroup();
        String jobName = scheduleJob.getJobName();
        if (!this.checkJob(jobName, jobGroup)) {
            return;
        }
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        schedulerFactoryBean.getScheduler().deleteJob(jobKey);
    }

    /**
     * 立即执行job
     *
     * @param scheduleJob 必要参数：JobName，JobGroup
     * @return void
     * @author LiuYongTao
     * @date 2019/8/16 11:12
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void runAJobNow(ScheduleJobVo scheduleJob) throws SchedulerException {
        String jobGroup = scheduleJob.getJobGroup();
        String jobName = scheduleJob.getJobName();
        if (!this.checkJob(jobName, jobGroup)) {
            return;
        }
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        schedulerFactoryBean.getScheduler().triggerJob(jobKey);
    }

    /**
     * 更新 cron 表达式，如果触发器不存在，则新生成一个
     *
     * @param job 必要参数：JobName，JobGroup，CronExpression
     * @return void
     * @author LiuYongTao
     * @date 2019/8/16 10:36
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCron(ScheduleJobVo job) throws Exception {
        String jobName = job.getJobName();
        String jobGroup = job.getJobGroup();
        if (!this.checkJob(jobName, jobGroup)) {
            return;
        }
        String cronExpression = job.getCronExpression();
        if (ToolsUtils.isEmpty(cronExpression)) {
            log.error("JobGroup:{}, JobName:{}, cron 表达式不能为空", jobGroup, jobName);
            throw new RuntimeException(jobName + "-" + jobGroup + ",cron 表达式不能为空");
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 设置调度的时间规则
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        if (trigger == null) {
            log.warn("jobGroup{},jobName:{},trigger 不存在,创建新 trigger", jobGroup, jobName);
            trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();
        } else {
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        }
        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 添加任务,如果任务存在则不添加。如果触发器不存在，则新建一个
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param job
     * @throws SchedulerException
     * @date 2017年5月7日 下午5:18:37
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addJob(ScheduleJobVo job) throws Exception {
        String jobName = job.getJobName();
        String jobGroup = job.getJobGroup();
        // 检查任务是否存在
        if (this.isJobExist(jobName, jobGroup)) {
            log.warn("jobGroup{},jobName:{} 已经存在", jobGroup, jobName);
            return;
        }
        // 检查cron表达式是否为空
        if(ToolsUtils.isEmpty(job.getCronExpression())){
            log.warn("CronExpression 为空，不通创建触发器，添加 Job");
            return;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            log.warn("jobGroup{},jobName:{},trigger 不存在,创建新 trigger", jobGroup, jobName);
            // 设置调度的时间规则
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            // 创建一个SimpleTrigger实例，指定该Trigger在Scheduler中所属组及名称
            trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();
        }
        Class<? extends Job> clazz;
        if (AutoTaskJobConcurrentEnum.CONCURRENT_NOT.getIsConcurrent().equals(job.getIsConcurrent())) {
            clazz = QuartzJobFactory.class;
        } else {
            clazz = QuartzJobFactoryDisallowConcurrentExecution.class;
        }
        // 指定Job在Scheduler中所属组及名称
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroup).build();
        jobDetail.getJobDataMap().put(JobCst.SCHEDULE_JOB_VO, job);
        // 注册并进行调度
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 批量添加任务
     *
     * @param list
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
        if (!this.checkJob(jobName, jobGroup)) {
            return null;
        }

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
    public boolean isJobExist(String jobName, String jobGroup) throws SchedulerException {
        if (ToolsUtils.isEmpty(jobName) || ToolsUtils.isEmpty(jobGroup)) {
            log.error("JobGroup:{},JobName:{} 不能为空", jobGroup, jobName);
            throw new RuntimeException(jobName + "-" + jobGroup + ",不能为空");
        }
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        return scheduler.checkExists(jobKey);
    }

    /**
     * 执行操作前的校验,如果jobName、jobGroup中有任务一个为空，则抛出异常。如果job存在，返回false
     *
     * @param jobName
     * @param jobGroup
     * @return boolean
     * @author LiuYongTao
     * @date 2019/8/16 11:01
     */
    private boolean checkJob(String jobName, String jobGroup) throws SchedulerException {
        if (ToolsUtils.isEmpty(jobName) || ToolsUtils.isEmpty(jobGroup)) {
            log.error("JobGroup:{},JobName:{} 不能为空", jobGroup, jobName);
            throw new RuntimeException(jobName + "-" + jobGroup + ",不能为空");
        }
        if (!this.isJobExist(jobName, jobGroup)) {
            log.warn("jobGroup{},jobName:{},任务不存在", jobGroup, jobName);
            return false;
        }
        return true;
    }
}
