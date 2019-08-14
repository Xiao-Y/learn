package com.billow.job.pojo.po;


import com.billow.common.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("任务名称")
    private String jobName;

    @ApiModelProperty("任务分组")
    private String jobGroup;

    @ApiModelProperty("任务状态 是否启动任务.0禁用 1启用 2删除")
    private String jobStatus;

    @ApiModelProperty("cron表达式")
    private String cronExpression;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("任务执行时调用哪个类的方法 包名+类名")
    private String beanClass;

    @ApiModelProperty("任务是否有状态,任务是否有状态,0-无，1-有")
    private String isConcurrent;

    @ApiModelProperty("spring bean")
    private String springId;

    @ApiModelProperty("任务调用的方法名")
    private String methodName;

    @ApiModelProperty("异常时，是否停止自动任务")
    private Boolean isStop;

}
