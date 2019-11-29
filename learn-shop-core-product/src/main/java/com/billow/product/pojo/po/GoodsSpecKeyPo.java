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
 * 规格表
 * </p>
 *
 * @author billow
 * @since 2019-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_spec_key")
@ApiModel(value="GoodsSpecKeyPo对象", description="规格表")
public class GoodsSpecKeyPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格编号")
    private String specNo;

    @ApiModelProperty(value = "规格名称")
    private String specName;

    @ApiModelProperty(value = "规格排序")
    private Long keySort;

    @ApiModelProperty(value = "分类id")
    private String categoryId;


}
