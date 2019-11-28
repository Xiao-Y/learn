package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * spu规格表
 * </p>
 *
 * @author billow
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_spu_spec")
@ApiModel(value="GoodsSpuSpecPo对象", description="spu规格表")
public class GoodsSpuSpecPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spu_id")
    private String spuId;

    @ApiModelProperty(value = "spec_key_id")
    private String specKeyId;


}
