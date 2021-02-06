package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sku增值保障
 * </p>
 *
 * @author billow
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_sku_safeguard")
@ApiModel(value="GoodsSkuSafeguardPo对象", description="sku增值保障")
public class GoodsSkuSafeguardPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "safeguard_id")
    @TableField("safeguard_id")
    private Long safeguardId;


}
