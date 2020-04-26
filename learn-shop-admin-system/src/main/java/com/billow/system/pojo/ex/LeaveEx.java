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

    @ApiModelProperty("指定流程路线标志")
    private String transFlag;

//    @ApiModelProperty("申请状态，0-提交申请，1-重新提交,2-取消申请，3-同意，4-驳回")
//    private String approveStatus;

//    @ApiModelProperty("任务ID")
//    private String taskId;

    @ApiModelProperty("指定流程处理人")
    private String assignee;

    @ApiModelProperty("指定任务节点")
    private String taskCode;

    @ApiModelProperty("提交类型:submit-提交，reSubmit-重新提交,cancel-取消申请，agree-同意，reject-驳回")
    private String submitType;
}
