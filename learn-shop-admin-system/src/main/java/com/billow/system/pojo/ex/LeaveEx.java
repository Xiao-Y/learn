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

    @ApiModelProperty("请假开始时间")
    private Date startDate;

    @ApiModelProperty("请假结束时间")
    private Date endDate;

    @ApiModelProperty("请假原因")
    private String reason;

    @ApiModelProperty("批注")
    private String comment;

    @ApiModelProperty("经理审核，true-通过，false-驳回")
    private String deptLeaderApprove;
}
