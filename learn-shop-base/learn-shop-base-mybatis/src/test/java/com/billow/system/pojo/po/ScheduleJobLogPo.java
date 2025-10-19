package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2025-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_schedule_job_log")
@Schema(title = "ScheduleJobLogPo对象", description="")
public class ScheduleJobLogPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("log_id")
    private String logId;

    @TableField("job_group")
    private String jobGroup;

    @TableField("job_id")
    private Long jobId;

    @TableField("job_name")
    private String jobName;

    @TableField("is_success")
    private Boolean isSuccess;

    @TableField("run_time")
    private String runTime;

    @TableField("info")
    private String info;


}
