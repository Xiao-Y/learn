package com.billow.product.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-31
 */
@Data
@Accessors(chain = true)
public class SeckillLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员id")
    private Integer memberId;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "会员电话")
    private String memberPhone;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "会员订阅时间")
    private LocalDateTime subscribeTime;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;


}
