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
 * 规格值表
 * </p>
 *
 * @author billow
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_spec_value")
@ApiModel(value="GoodsSpecValuePo对象", description="规格值表")
public class GoodsSpecValuePo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格id")
    @TableField("spec_key_id")
    private Long specKeyId;

    @ApiModelProperty(value = "规格值")
    @TableField("spec_value")
    private String specValue;

    @ApiModelProperty(value = "规格排序")
    @TableField("value_sort")
    private Long valueSort;


}
