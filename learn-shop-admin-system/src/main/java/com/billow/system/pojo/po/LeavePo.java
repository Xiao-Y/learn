package com.billow.system.pojo.po;

import com.billow.common.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 请假申请
 *
 * @author liuyongtao
 * @create 2019-09-01 14:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "u_leave")
public class LeavePo extends BasePo implements Serializable {

    @ApiModelProperty("请假开始时间")
    private Date startDate;

    @ApiModelProperty("请假结束时间")
    private Date endDate;

    @ApiModelProperty("请假原因")
    private String reason;
}
