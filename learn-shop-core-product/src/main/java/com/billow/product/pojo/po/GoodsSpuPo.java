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
 * spu表
 * </p>
 *
 * @author billow
 * @since 2019-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_spu")
@ApiModel(value="GoodsSpuPo对象", description="spu表")
public class GoodsSpuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号，唯一")
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "最低售价")
    private Integer lowPrice;

    @ApiModelProperty(value = "总库存量")
    private Long stock;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "品牌id")
    private String brandId;

    @ApiModelProperty(value = "商品排序")
    private Long spuSort;


}
