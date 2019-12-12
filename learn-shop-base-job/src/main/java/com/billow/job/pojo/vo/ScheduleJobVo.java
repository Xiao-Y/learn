package com.billow.job.pojo.vo;


import com.billow.job.pojo.po.ScheduleJobPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleJobVo extends ScheduleJobPo implements Serializable {

    private static final long serialVersionUID = -549836299658030636L;

    // 运行状态
    private String statusName;

    // 任务是否有状态,0-无（单线程），1-有（多线程）
    private String isConcurrentName;

    // 验自动任务添加、修改时参数的设置
    private String message;

    // 旧的任务分组
    private String oldJobGroup;

    // 旧的任务名称
    private String oldJobName;

    // 执行时间，分钟
    private String runTime;
    // 自动任务日志id,唯一
    private String logId;
}
