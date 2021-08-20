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
 * 商品规格参数模板，json格式。
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_specification")
@ApiModel(value="GoodsSpecificationPo对象", description="商品规格参数模板，json格式。")
public class GoodsSpecificationPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格模板所属商品分类id")
    @TableId(value = "category_id", type = IdType.ID_WORKER)
    private Long categoryId;

    @ApiModelProperty(value = "规格参数模板，json格式")
    @TableField("specifications")
    private String specifications;


}
