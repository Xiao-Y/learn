package com.billow.product.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-31
 */
@Data
@Accessors(chain = true)
public class SeckillVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "开始日期")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "结束日期")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "上下线状态")
    private Integer status;


}
