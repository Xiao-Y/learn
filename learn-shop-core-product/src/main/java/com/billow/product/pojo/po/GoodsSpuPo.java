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
 * spu表（Standard Product Unit, 标准产品单元）
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_spu")
@ApiModel(value="GoodsSpuPo对象", description="spu表（Standard Product Unit, 标准产品单元）")
public class GoodsSpuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号，唯一")
    @TableField("spu_no")
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty(value = "子标题")
    @TableField("sub_title")
    private String subTitle;

    @ApiModelProperty(value = "最低售价")
    @TableField("low_price")
    private Integer lowPrice;

    @ApiModelProperty(value = "总库存量")
    @TableField("stock")
    private Long stock;

    @ApiModelProperty(value = "分类id")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "1级类目id")
    @TableField("cid1")
    private Long cid1;

    @ApiModelProperty(value = "2级类目id")
    @TableField("cid2")
    private Long cid2;

    @ApiModelProperty(value = "3级类目id")
    @TableField("cid3")
    private Long cid3;

    @ApiModelProperty(value = "品牌id")
    @TableField("brand_id")
    private Long brandId;

    @ApiModelProperty(value = "商品排序")
    @TableField("spu_sort")
    private Long spuSort;


}
