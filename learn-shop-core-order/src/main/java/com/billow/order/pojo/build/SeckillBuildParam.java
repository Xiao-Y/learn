package com.billow.order.pojo.build;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀库存表 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-21
 */
@Data
@Accessors(chain = true)
public class SeckillBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku编号,唯一")
    private String skuNo;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "库存数量")
    private Integer stock;

    @ApiModelProperty(value = "秒杀开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "秒杀结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "支付过期时间（单位：分钟）")
    private Long paymentExp;

    @ApiModelProperty(value = "是否已经加载过")
    private Boolean loadCache;

    @ApiModelProperty(value = "商品说明")
    private String remarks;


}
