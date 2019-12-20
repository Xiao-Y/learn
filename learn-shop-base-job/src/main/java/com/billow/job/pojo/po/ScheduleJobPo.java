package com.billow.job.pojo.po;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 自动任务
 *
 * @author liuyongtao
 * @date 2017年5月8日 上午10:30:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
// sys_schedule_job
public class ScheduleJobPo extends BasePo implements Serializable {
    // 任务名称
    private String jobName;
    // 任务分组
    private String jobGroup;
    // 任务状态 是否启动任务.0-禁用 1-启用
    private String jobStatus;
    // cron表达式
    private String cronExpression;
    // 描述
    private String description;
    // 任务执行时调用类的类型，1-springId，2-beanClass,3-http,4-mq
    private String classType;
    // 任务执行时调用类或者springId
    private String runClass;
    // http 请求的 url
    private String httpUrl;
    // mq 的路由 key
    private String routingKey;
    // 任务是否有状态,任务是否有状态,0-无，1-有
    private String isConcurrent;
    // 任务调用的方法名
    private String methodName;
    // 异常时，是否停止自动任务
    private Boolean isExceptionStop;
    // 是否记录日志
    private Boolean isSaveLog;
    // 是否发送邮件,0-不发送,1-发送,2-异常时发送,3-成功时发送
    private String isSendMail;
    // 邮件模板ID
    private Long templateId;
    // 邮件接收人,多个时,用;分开
    private String mailReceive;

}
