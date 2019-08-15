package com.billow.job.pojo.vo;


import com.billow.job.pojo.po.ScheduleJobPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleJobVo extends ScheduleJobPo implements Serializable {

    private static final long serialVersionUID = -549836299658030636L;

    @ApiModelProperty("运行状态")
    private String statusName;

    @ApiModelProperty("任务是否有状态,0-无（单线程），1-有（多线程）")
    private String isConcurrentName;

    @ApiModelProperty("验自动任务添加、修改时参数的设置")
    private String message;

    @ApiModelProperty("旧的任务分组")
    private String oldJobGroup;

    @ApiModelProperty("旧的任务名称")
    private String oldJobName;
}
