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
 * sku表（stock keeping uint 库存量单位）
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_goods_sku")
@ApiModel(value="GoodsSkuPo对象", description="sku表（stock keeping uint 库存量单位）")
public class GoodsSkuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku编号,唯一")
    @TableField("sku_no")
    private String skuNo;

    @ApiModelProperty(value = "sku名称(冗余spu_name)")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty(value = "售价")
    @TableField("price")
    private Integer price;

    @ApiModelProperty(value = "库存")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty(value = "锁定库存")
    @TableField("lock_stock")
    private Integer lockStock;

    @ApiModelProperty(value = "预警库存")
    @TableField("low_stock")
    private Integer lowStock;

    @ApiModelProperty(value = "展示图片")
    @TableField("pic")
    private String pic;

    @ApiModelProperty(value = "销量")
    @TableField("sale")
    private Integer sale;

    @ApiModelProperty(value = "商铺id,为0表示自营")
    @TableField("shop_id")
    private Long shopId;

    @ApiModelProperty(value = "spu_id")
    @TableField("spu_id")
    private Long spuId;


}
