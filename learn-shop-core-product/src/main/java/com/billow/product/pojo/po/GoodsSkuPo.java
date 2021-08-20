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
 * sku表（stock keeping uint 库存量单位）
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_sku")
@ApiModel(value="GoodsSkuPo对象", description="sku表（stock keeping uint 库存量单位）")
public class GoodsSkuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "spu_id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "sku编号,唯一")
    @TableField("sku_no")
    private String skuNo;

    @ApiModelProperty(value = "商品标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "sku名称(冗余spu_name)")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty(value = "销售价格，单位为分")
    @TableField("price")
    private Integer price;

    @ApiModelProperty(value = "库存")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty(value = "商品的图片，多个图片以‘,’分割")
    @TableField("images")
    private String images;


}
