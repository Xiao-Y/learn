package com.billow.product.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2019-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_sku_spec_value")
@ApiModel(value="GoodsSkuSpecValuePo对象", description="sku规格值")
public class GoodsSkuSpecValuePo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku_id")
    private String skuId;

    @ApiModelProperty(value = "规格id(冗余)")
    private String specKeyId;

    @ApiModelProperty(value = "规格值id")
    private String specValueId;

    @ApiModelProperty(value = "规格值排序")
    private Long skuSpecSort;


}
