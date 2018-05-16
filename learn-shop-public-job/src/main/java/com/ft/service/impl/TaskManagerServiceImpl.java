package com.ft.service.impl;


import com.ft.constant.MessageTipsCst;
import com.ft.core.enumType.AutoTaskJobStatusEnum;
import com.ft.core.manager.QuartzManager;
import com.ft.generator.UUID;
import com.ft.model.custom.JsonResult;
import com.ft.model.expand.ScheduleJobDto;
import com.ft.model.expand.ScheduleJobLogDto;
import com.ft.service.ScheduleJobLogService;
import com.ft.service.ScheduleJobService;
import com.ft.service.TaskManagerService;
import com.ft.utlis.SpringContextUtil;
import com.ft.utlis.ToolsUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@Service
public class TaskManagerServiceImpl implements TaskManagerService {

    @Autowired
    private QuartzManager quartzManager;

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    @Override
    @Transactional
    public void updateJobStatus(ScheduleJobDto dto) throws Exception {
        ScheduleJobDto scheduleJobDto = scheduleJobService.selectByPrimaryKey(dto);
        if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(dto.getJobStatus())) {
            quartzManager.addJob(scheduleJobDto);
        } else if (AutoTaskJobStatusEnum.JOB_STATUS_PAUSE.getStatus().equals(dto.getJobStatus())) {
            quartzManager.pauseJob(scheduleJobDto);
        }
        scheduleJobService.updateByPrimaryKeySelective(dto);
    }

    @Override
    @Transactional
    public void deleteAutoTask(int jobId) throws Exception {
        ScheduleJobDto dto = new ScheduleJobDto();
        dto.setJobId(jobId);
        ScheduleJobDto scheduleJobDto = scheduleJobService.selectByPrimaryKey(dto);
        quartzManager.deleteJob(scheduleJobDto);
        scheduleJobService.deleteByPrimaryKey(dto);
    }

    @Override
    @Transactional
    public void saveAutoTask(ScheduleJobDto scheduleJobDto) throws Exception {
        Integer jobId = scheduleJobDto.getJobId();
        String jobStatus = scheduleJobDto.getJobStatus();
        if (null == jobId) {// 表示添加
            scheduleJobDto.setUpdateTime(new Date());
            scheduleJobDto.setCreateTime(new Date());
            scheduleJobService.insert(scheduleJobDto);
            if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(jobStatus)) {
                quartzManager.addJob(scheduleJobDto);
            }
        } else {// 表示更新
            scheduleJobDto.setUpdateTime(new Date());
            scheduleJobService.updateByPrimaryKeySelective(scheduleJobDto);
            JobDetail jobDetail = quartzManager.getJobDetail(scheduleJobDto.getJobName(), scheduleJobDto.getJobGroup());
            if (jobDetail != null) {
                if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(jobStatus)) {
                    quartzManager.addJob(scheduleJobDto);
                } else if (AutoTaskJobStatusEnum.JOB_STATUS_PAUSE.getStatus().equals(jobStatus)) {
                    quartzManager.pauseJob(scheduleJobDto);
                }
            } else {
                if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(jobStatus)) {
                    quartzManager.addJob(scheduleJobDto);
                }
            }
        }
    }

    @Override
    public JsonResult checkAutoTask(ScheduleJobDto scheduleJobDto) throws Exception {
        JsonResult json = new JsonResult();
        json.setType(MessageTipsCst.TYPE_SUCCES);
        json.setMessage("");

        String jobStatus = scheduleJobDto.getJobStatus();
        String cronExpression = scheduleJobDto.getCronExpression();
        String springId = scheduleJobDto.getSpringId();
        String beanClass = scheduleJobDto.getBeanClass();
        String methodName = scheduleJobDto.getMethodName();

        if (ToolsUtils.isNotEmpty(cronExpression)) {
            try {
                CronScheduleBuilder.cronSchedule(cronExpression);
            } catch (Exception e) {
                json.setType(MessageTipsCst.TYPE_ERROR);
                json.setMessage("cron表达式错误，请查证！");
            }
        } else if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(jobStatus)) {
            // bean能否获取标识
            boolean beanFlag = true;
            Class<?> clazz = null;
            // bean相关检查
            if (ToolsUtils.isNotEmpty(springId)) {
                try {
                    Object bean = SpringContextUtil.getBean(springId);
                    clazz = bean.getClass();
                } catch (Exception e) {
                    json.setType(MessageTipsCst.TYPE_ERROR);
                    json.setMessage("springId错误，未获取相关Bean！");
                    beanFlag = false;
                }
            } else {
                try {
                    clazz = Class.forName(beanClass);
                    clazz.newInstance();
                } catch (Exception e) {
                    json.setType(MessageTipsCst.TYPE_ERROR);
                    json.setMessage("beanClass错误，未获取相关类！");
                    beanFlag = false;
                }
            }
            if (!beanFlag) {
                return json;
            }
            // 对执行方法检查（bean可以获取）
            if (ToolsUtils.isNotEmpty(methodName)) {
                try {
                    clazz.getDeclaredMethod(methodName);
                } catch (NoSuchMethodException | SecurityException e) {
                    json.setType(MessageTipsCst.TYPE_ERROR);
                    json.setMessage("方法：" + methodName + "，未获取！");
                }
            }
        }
        return json;
    }

    @Override
    public void immediateExecutionTask(ScheduleJobDto scheduleJobDto) throws Exception {
        quartzManager.runAJobNow(scheduleJobDto);
    }

    @Override
    public void insertAutoTaskException(JobExecutionContext jobExecutionContext, Exception exception) throws Exception {
        ScheduleJobDto scheduleJob = (ScheduleJobDto) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        ScheduleJobDto jobdto = new ScheduleJobDto();
        jobdto.setJobId(scheduleJob.getJobId());
        jobdto.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_EXCEPTION.getStatus());
        scheduleJobService.updateJobStatus(jobdto);
        if (exception != null) {
            StringWriter sw = new StringWriter();
            exception.printStackTrace(new PrintWriter(sw, true));
            ScheduleJobLogDto logDto = new ScheduleJobLogDto();
            logDto.setId(UUID.generate());
            logDto.setJobId(scheduleJob.getJobId());
            logDto.setJobGroup(scheduleJob.getJobGroup());
            logDto.setJobName(scheduleJob.getJobName());
            logDto.setCreateTime(new Date());
            logDto.setInfo(sw.toString());
            scheduleJobLogService.insert(logDto);
        }
    }
}
