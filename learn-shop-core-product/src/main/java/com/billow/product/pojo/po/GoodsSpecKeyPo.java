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
 * 规格表
 * </p>
 *
 * @author billow
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_spec_key")
@ApiModel(value="GoodsSpecKeyPo对象", description="规格表")
public class GoodsSpecKeyPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格编号")
    @TableField("spec_no")
    private String specNo;

    @ApiModelProperty(value = "规格名称")
    @TableField("spec_name")
    private String specName;

    @ApiModelProperty(value = "规格排序")
    @TableField("key_sort")
    private Long keySort;


}
