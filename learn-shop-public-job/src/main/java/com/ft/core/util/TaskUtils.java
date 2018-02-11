package com.ft.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;


import com.ft.core.enumType.AutoTaskJobStatusEnum;
import com.ft.generator.UUID;
import com.ft.model.expand.ScheduleJobDto;
import com.ft.model.expand.ScheduleJobLogDto;
import com.ft.service.ScheduleJobLogService;
import com.ft.service.ScheduleJobService;
import com.ft.utlis.BeanUtils;
import com.ft.utlis.ToolsUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自动任务工具类
 *
 * @author liuyongtao
 * @date 2017年5月8日 上午10:24:57
 */
public class TaskUtils {

    public final static Logger log = Logger.getLogger(TaskUtils.class);

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(ScheduleJobDto scheduleJob) throws Exception {
        Object object = null;
        Class<?> clazz = null;
        Method method = null;
        boolean expFlag = false;
        Exception exception = new Exception();
        try {
            // springId不为空先按springId查找bean
            if (ToolsUtils.isNotEmpty(scheduleJob.getSpringId())) {
                object = BeanUtils.getBean(scheduleJob.getSpringId());
            } else if (ToolsUtils.isNotEmpty(scheduleJob.getBeanClass())) {
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = clazz.newInstance();
            }
            if (object == null) {
                throw new RuntimeException("任务名称 = [" + scheduleJob.getJobName() + "] 未启动成功，请检查是否配置正确！！！");
            }
            clazz = object.getClass();
            // 获取自动任务要执行的方法
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
            if (method == null) {
                throw new RuntimeException("任务名称 = [" + scheduleJob.getJobName() + "] 方法名设置错误！！！");
            }
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
            expFlag = true;
            exception = e;
            throw e;
        } finally {
            if (expFlag) {//发生异常停止这个自动任务，写异常日志
                try {
                    ScheduleJobDto dto = new ScheduleJobDto();
                    dto.setJobId(scheduleJob.getJobId());
                    dto.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_EXCEPTION.getStatus());
                    ScheduleJobService scheduleJobService = BeanUtils.getBean("scheduleJobServiceImpl");
                    scheduleJobService.updateJobStatus(dto);
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
                        ScheduleJobLogService scheduleJobLogService = BeanUtils.getBean("scheduleJobLogServiceImpl");
                        scheduleJobLogService.insert(logDto);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e);
                }
            }
        }
    }
}
