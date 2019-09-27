package com.billow.job.pojo.po;


import com.billow.job.pojo.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 自动任务
 *
 * @author liuyongtao
 * @date 2017年5月8日 上午10:30:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_schedule_job")
public class ScheduleJobPo extends BasePo implements Serializable {

    private static final long serialVersionUID = 6347036356689325574L;

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

    // 任务执行时调用类的类型，1-springId，2-beanClass
    private String classType;

    // 任务执行时调用类或者springId
    private String runClass;

//    // 任务执行时调用哪个类的方法 包名+类名
//    private String beanClass;
//
//    // spring bean
//    private String springId;

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
