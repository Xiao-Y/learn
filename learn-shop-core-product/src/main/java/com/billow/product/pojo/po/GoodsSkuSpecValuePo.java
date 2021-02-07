package com.billow.product.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sku规格值
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_sku_spec_value")
@ApiModel(value="GoodsSkuSpecValuePo对象", description="sku规格值")
public class GoodsSkuSpecValuePo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spu_id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "规格id(冗余)")
    @TableField("spec_key_id")
    private Long specKeyId;

    @ApiModelProperty(value = "规格值id")
    @TableField("spec_value_id")
    private Long specValueId;

    @ApiModelProperty(value = "规格值排序")
    @TableField("sku_spec_sort")
    private Long skuSpecSort;


}
