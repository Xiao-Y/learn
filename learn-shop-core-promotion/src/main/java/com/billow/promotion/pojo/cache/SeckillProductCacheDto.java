package com.billow.promotion.pojo.cache;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-27
 */
@Data
@Accessors(chain = true)
public class SeckillProductCacheDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "限时购id")
    private Long seckillId;

    @Schema(title = "编号")
    private Long seckillSessionId;

    @Schema(title = "商品id")
    private Long productId;

    @Schema(title = "skuid")
    private Long skuId;

    @Schema(title = "限时购价格")
    private BigDecimal seckillPrice;

    @Schema(title = "限时购数量")
    private Integer seckillCount;

    @Schema(title = "每人限购数量")
    private Integer seckillLimit;

    @Schema(title = "排序")
    private Integer sort;
}
