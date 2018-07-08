package com.billow.service.impl;


import com.billow.common.constant.MessageTipsCst;
import com.billow.core.enumType.AutoTaskJobStatusEnum;
import com.billow.core.manager.QuartzManager;
import com.billow.pojo.vo.ScheduleJobVo;
import com.billow.tools.generator.UUID;
import com.billow.pojo.ex.JsonResult;
import com.billow.pojo.vo.ScheduleJobLogVo;
import com.billow.service.ScheduleJobLogService;
import com.billow.service.ScheduleJobService;
import com.billow.service.TaskManagerService;
import com.billow.tools.utlis.SpringContextUtil;
import com.billow.tools.utlis.ToolsUtils;
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
    public void updateJobStatus(ScheduleJobVo dto) throws Exception {
        ScheduleJobVo scheduleJobVo = scheduleJobService.selectByPrimaryKey(dto);
        if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(dto.getJobStatus())) {
            quartzManager.addJob(scheduleJobVo);
        } else if (AutoTaskJobStatusEnum.JOB_STATUS_PAUSE.getStatus().equals(dto.getJobStatus())) {
            quartzManager.pauseJob(scheduleJobVo);
        }
        scheduleJobService.updateByPrimaryKeySelective(dto);
    }

    @Override
    @Transactional
    public void deleteAutoTask(Long jobId) throws Exception {
        ScheduleJobVo dto = new ScheduleJobVo();
        dto.setId(jobId);
        ScheduleJobVo scheduleJobVo = scheduleJobService.selectByPrimaryKey(dto);
        quartzManager.deleteJob(scheduleJobVo);
        scheduleJobService.deleteByPrimaryKey(dto);
    }

    @Override
    @Transactional
    public void saveAutoTask(ScheduleJobVo scheduleJobVo) throws Exception {
        Long jobId = scheduleJobVo.getId();
        String jobStatus = scheduleJobVo.getJobStatus();
        if (null == jobId) {// 表示添加
            scheduleJobVo.setUpdateTime(new Date());
            scheduleJobVo.setCreateTime(new Date());
            scheduleJobService.insert(scheduleJobVo);
            if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(jobStatus)) {
                quartzManager.addJob(scheduleJobVo);
            }
        } else {// 表示更新
            scheduleJobVo.setUpdateTime(new Date());
            scheduleJobService.updateByPrimaryKeySelective(scheduleJobVo);
            JobDetail jobDetail = quartzManager.getJobDetail(scheduleJobVo.getJobName(), scheduleJobVo.getJobGroup());
            if (jobDetail != null) {
                if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(jobStatus)) {
                    quartzManager.addJob(scheduleJobVo);
                } else if (AutoTaskJobStatusEnum.JOB_STATUS_PAUSE.getStatus().equals(jobStatus)) {
                    quartzManager.pauseJob(scheduleJobVo);
                }
            } else {
                if (AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus().equals(jobStatus)) {
                    quartzManager.addJob(scheduleJobVo);
                }
            }
        }
    }

    @Override
    public JsonResult checkAutoTask(ScheduleJobVo scheduleJobVo) throws Exception {
        JsonResult json = new JsonResult();
        json.setType(MessageTipsCst.TYPE_SUCCES);
        json.setMessage("");

        String jobStatus = scheduleJobVo.getJobStatus();
        String cronExpression = scheduleJobVo.getCronExpression();
        String springId = scheduleJobVo.getSpringId();
        String beanClass = scheduleJobVo.getBeanClass();
        String methodName = scheduleJobVo.getMethodName();

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
    public void immediateExecutionTask(ScheduleJobVo scheduleJobVo) throws Exception {
        quartzManager.runAJobNow(scheduleJobVo);
    }

    @Override
    public void insertAutoTaskException(JobExecutionContext jobExecutionContext, Exception exception) throws Exception {
        ScheduleJobVo scheduleJob = (ScheduleJobVo) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        ScheduleJobVo jobdto = new ScheduleJobVo();
        jobdto.setId(scheduleJob.getId());
        jobdto.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_EXCEPTION.getStatus());
        scheduleJobService.updateJobStatus(jobdto);
        if (exception != null) {
            StringWriter sw = new StringWriter();
            exception.printStackTrace(new PrintWriter(sw, true));
            ScheduleJobLogVo logDto = new ScheduleJobLogVo();
//            logDto.setId(UUID.generate());
            logDto.setId(scheduleJob.getId());
            logDto.setJobGroup(scheduleJob.getJobGroup());
            logDto.setJobName(scheduleJob.getJobName());
            logDto.setCreateTime(new Date());
            logDto.setInfo(sw.toString());
            scheduleJobLogService.insert(logDto);
        }
    }
}
