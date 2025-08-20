package com.billow.order.pojo.build;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "sku编号,唯一")
    private String skuNo;

    @Schema(title = "商品名称")
    private String goodsName;

    @Schema(title = "库存数量")
    private Integer stock;

    @Schema(title = "秒杀开始时间")
    private LocalDateTime startTime;

    @Schema(title = "秒杀结束时间")
    private LocalDateTime endTime;

    @Schema(title = "支付过期时间（单位：分钟）")
    private Long paymentExp;

    @Schema(title = "是否已经加载过")
    private Boolean loadCache;

    @Schema(title = "商品说明")
    private String remarks;


}
