package com.billow.job.pojo.po;

import com.billow.common.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 自动任务信息日志数据库模型<br>
 * <p>
 * 对应的表名：sys_schedule_job_log
 *
 * @author billow<br>
 * @version 2.0
 * @Mail lyongtao123@126.com<br>
 * @date 2017-12-08 15:46:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_schedule_job_log")
public class ScheduleJobLogPo extends BasePo implements Serializable {

    @ApiModelProperty("任务名称")
    private String jobName;

    @ApiModelProperty("任务分组")
    private String jobGroup;

    @ApiModelProperty("自动任务id")
    private Long jobId;

    @Lob
    @Type(type = "text")
    @ApiModelProperty("错误信息")
    private String info;

    @ApiModelProperty("是否执行成功")
    private Boolean isSuccess;

    @ApiModelProperty("执行时间")
    private String runTime;

}  