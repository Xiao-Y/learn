package com.billow.util;

import com.billow.core.enumType.AutoTaskJobStatusEnum;
import com.billow.model.expand.ScheduleJobDto;
import com.billow.model.expand.ScheduleJobLogDto;
import com.billow.service.ScheduleJobLogService;
import com.billow.service.ScheduleJobService;
import com.billow.tools.generator.UUID;
import com.billow.tools.utlis.SpringContextUtil;
import com.billow.tools.utlis.ToolsUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;

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
                object = SpringContextUtil.getBean(scheduleJob.getSpringId());
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
                    ScheduleJobService scheduleJobService = SpringContextUtil.getBean("scheduleJobServiceImpl");
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
                        ScheduleJobLogService scheduleJobLogService = SpringContextUtil.getBean("scheduleJobLogServiceImpl");
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
