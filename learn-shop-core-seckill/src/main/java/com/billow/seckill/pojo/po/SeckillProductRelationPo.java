package com.billow.seckill.pojo.po;

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
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sms_seckill_product_relation")
@ApiModel(value="SeckillProductRelationPo对象", description="限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。")
public class SeckillProductRelationPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "限时购id")
    @TableField("flash_promotion_id")
    private Long flashPromotionId;

    @ApiModelProperty(value = "编号")
    @TableField("flash_promotion_session_id")
    private Long flashPromotionSessionId;

    @ApiModelProperty(value = "商品价格")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty(value = "限时购价格")
    @TableField("flash_promotion_price")
    private BigDecimal flashPromotionPrice;

    @ApiModelProperty(value = "限时购数量")
    @TableField("flash_promotion_count")
    private Integer flashPromotionCount;

    @ApiModelProperty(value = "每人限购数量")
    @TableField("flash_promotion_limit")
    private Integer flashPromotionLimit;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;


}
