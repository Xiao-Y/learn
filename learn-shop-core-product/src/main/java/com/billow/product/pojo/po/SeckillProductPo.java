package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("sms_seckill_product")
@ApiModel(value="SeckillProductPo对象", description="限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。")
public class SeckillProductPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "限时购id")
    @TableField("seckill_id")
    private Long seckillId;

    @ApiModelProperty(value = "编号")
    @TableField("seckill_session_id")
    private Long seckillSessionId;

    @ApiModelProperty(value = "商品id")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty(value = "skuid")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "限时购价格")
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    @ApiModelProperty(value = "限时购库存数量")
    @TableField("seckill_count")
    private Integer seckillCount;

    @ApiModelProperty(value = "每人限购数量")
    @TableField("seckill_limit")
    private Integer seckillLimit;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;


}
