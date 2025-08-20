package com.billow.system.pojo.ex;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author liuyongtao
 * @create 2019-09-01 14:16
 */
@Data
public class LeaveEx {

    @Schema(title = "申请ID")
    private Long id;

    @Schema(title = "请假开始时间")
    private Date startDate;

    @Schema(title = "请假结束时间")
    private Date endDate;

    @Schema(title = "请假原因")
    private String reason;

    @Schema(title = "批注")
    private String comment;

    @Schema(title = "指定流程路线标志")
    private String transFlag;

//    @Schema(title = "申请状态，0-提交申请，1-重新提交,2-取消申请，3-同意，4-驳回")
//    private String approveStatus;

//    @Schema(title = "任务ID")
//    private String taskId;

    @Schema(title = "指定流程处理人")
    private String assignee;

    @Schema(title = "指定任务节点")
    private String taskCode;

    @Schema(title = "提交类型:submit-提交，reSubmit-重新提交,cancel-取消申请，agree-同意，reject-驳回")
    private String submitType;
}
