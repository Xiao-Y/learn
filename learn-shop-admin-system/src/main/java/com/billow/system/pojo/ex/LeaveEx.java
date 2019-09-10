package com.billow.system.pojo.ex;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author liuyongtao
 * @create 2019-09-01 14:16
 */
@Data
public class LeaveEx {

    @ApiModelProperty("申请ID")
    private Long id;

    @ApiModelProperty("请假开始时间")
    private Date startDate;

    @ApiModelProperty("请假结束时间")
    private Date endDate;

    @ApiModelProperty("请假原因")
    private String reason;

    @ApiModelProperty("批注")
    private String comment;

    @ApiModelProperty("经理审核，1-否定，2-肯定")
    private String moveFlag;

    @ApiModelProperty("申请状态，0-提交申请，1-退回")
    private String approveStatus;

    @ApiModelProperty("任务ID")
    private String taskId;
}
