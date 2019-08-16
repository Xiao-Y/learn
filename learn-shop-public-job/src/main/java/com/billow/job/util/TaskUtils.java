package com.billow.job.util;

import com.billow.job.core.enumType.AutoTaskJobStatusEnum;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.pojo.vo.ScheduleJobLogVo;
import com.billow.job.service.ScheduleJobLogService;
import com.billow.job.service.ScheduleJobService;
import com.billow.tools.date.DateTime;
import com.billow.tools.utlis.SpringContextUtil;
import com.billow.tools.utlis.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自动任务工具类
 *
 * @author liuyongtao
 * @date 2017年5月8日 上午10:24:57
 */
@Slf4j
public class TaskUtils {

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(ScheduleJobVo scheduleJob) throws Exception {
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
            log.error(e.getMessage());
//            expFlag = true;
//            exception = e;
            throw e;
        } finally {
//            if (expFlag) {//发生异常停止这个自动任务，写异常日志
//                try {
//                    ScheduleJobVo dto = new ScheduleJobVo();
//                    dto.setId(scheduleJob.getId());
//                    dto.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_EXCEPTION.getStatus());
//                    ScheduleJobService scheduleJobService = SpringContextUtil.getBean("scheduleJobServiceImpl");
//                    scheduleJobService.updateJobStatus(dto);
//                    if (exception != null) {
//                        StringWriter sw = new StringWriter();
//                        exception.printStackTrace(new PrintWriter(sw, true));
//                        ScheduleJobLogVo logDto = new ScheduleJobLogVo();
////                        logDto.setId(UUID.generate());
//                        logDto.setId(scheduleJob.getId());
//                        logDto.setJobGroup(scheduleJob.getJobGroup());
//                        logDto.setJobName(scheduleJob.getJobName());
//                        logDto.setCreateTime(new Date());
//                        logDto.setInfo(sw.toString());
//                        ScheduleJobLogService scheduleJobLogService = SpringContextUtil.getBean("scheduleJobLogServiceImpl");
//                        scheduleJobLogService.insert(logDto);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    log.error(e.getMessage());
//                }
//            }
        }
    }

    /**
     * cron 表达式计划运行时间
     *
     * @param cron  表达式
     * @param times 运行次数
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/8/13 9:31
     */
    public static List<String> runTime(String cron, int times) {
        List<String> rs = new ArrayList<>();
        if (cron == null || "".equals(cron.trim())) {
            return rs;
        }
        try {
            CronExpression ce = new CronExpression(cron);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date();
            for (int i = 0; i < times; i++) {
                d = ce.getNextValidTimeAfter(d);
                if (d != null) {
                    rs.add(format.format(d));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return rs;
    }

    public static void saveLog(ScheduleJobVo scheduleJob, Exception exception) {

        boolean isSuccess = true;
        if (exception != null) {
            isSuccess = false;
        }

        if (scheduleJob.getIsSaveLog()) {
            // 插入日志
            ScheduleJobLogVo logDto = new ScheduleJobLogVo();
            logDto.setJobId(scheduleJob.getId());
            logDto.setJobGroup(scheduleJob.getJobGroup());
            logDto.setJobName(scheduleJob.getJobName());
            logDto.setIsSuccess(isSuccess);
            logDto.setRunTime(scheduleJob.getRunTime());
            logDto.setCreateTime(new DateTime());
            logDto.setUpdateTime(new DateTime());
            if (exception != null) {
                StringWriter sw = new StringWriter();
                exception.printStackTrace(new PrintWriter(sw, true));
                logDto.setInfo(sw.toString());
                log.error(logDto.getInfo());
            }

            try {
                ScheduleJobLogService scheduleJobLogService = SpringContextUtil.getBean("scheduleJobLogServiceImpl");
                scheduleJobLogService.insert(logDto);
            } catch (Exception e) {
                log.error("自动任务日志插入失败：{}", e.getMessage());
            }
        }

        if (!isSuccess && scheduleJob.getIsExceptionStop()) {
            try {
                ScheduleJobService scheduleJobService = SpringContextUtil.getBean("scheduleJobServiceImpl");
                ScheduleJobVo scheduleJobVo = scheduleJobService.findByIdAndValidIndIsTrueAndIsStopIsTrue(scheduleJob.getId());
                if (scheduleJobVo != null) {
                    scheduleJobVo.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_EXCEPTION.getStatus());
                    scheduleJobService.updateByPk(scheduleJobVo);
                }
            } catch (Exception e) {
                log.error("自动任务修改失败：{}", e.getMessage());
            }
        }
    }
}
