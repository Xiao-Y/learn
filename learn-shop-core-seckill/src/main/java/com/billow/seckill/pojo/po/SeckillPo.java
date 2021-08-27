package com.billow.seckill.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。
 * </p>
 *
 * @author billow
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sms_seckill")
@ApiModel(value="SeckillPo对象", description="限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。")
public class SeckillPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "开始日期")
    @TableField("start_date")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "结束日期")
    @TableField("end_date")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "上下线状态")
    @TableField("status")
    private Integer status;


}
