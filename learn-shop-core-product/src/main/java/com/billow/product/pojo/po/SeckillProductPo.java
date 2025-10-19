package com.billow.product.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。
 * </p>
 *
 * @author billow
 * @since 2021-08-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("sms_seckill_product")
@Schema(title = "SeckillProductPo对象", description="限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。")
public class SeckillProductPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "限时购id")
    @Column("seckill_id")
    private Long seckillId;

    @Schema(title = "编号")
    @Column("seckill_session_id")
    private Long seckillSessionId;

    @Schema(title = "商品id")
    @Column("product_id")
    private Long productId;

    @Schema(title = "skuid")
    @Column("sku_id")
    private Long skuId;

    @Schema(title = "限时购价格")
    @Column("seckill_price")
    private BigDecimal seckillPrice;

    @Schema(title = "限时购库存数量")
    @Column("seckill_count")
    private Integer seckillCount;

    @Schema(title = "每人限购数量")
    @Column("seckill_limit")
    private Integer seckillLimit;

    @Schema(title = "排序")
    @Column("sort")
    private Integer sort;


}
