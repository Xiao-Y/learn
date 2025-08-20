package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "GoodsSkuPo对象", description="sku表（stock keeping uint 库存量单位）")
public class GoodsSkuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "sku编号,唯一")
    @TableField("sku_no")
    private String skuNo;

    @Schema(title = "sku名称(冗余spu_name)")
    @TableField("sku_name")
    private String skuName;

    @Schema(title = "售价")
    @TableField("price")
    private Integer price;

    @Schema(title = "库存")
    @TableField("stock")
    private Integer stock;

    @Schema(title = "锁定库存")
    @TableField("lock_stock")
    private Integer lockStock;

    @Schema(title = "预警库存")
    @TableField("low_stock")
    private Integer lowStock;

    @Schema(title = "展示图片")
    @TableField("pic")
    private String pic;

    @Schema(title = "销量")
    @TableField("sale")
    private Integer sale;

    @Schema(title = "商铺id,为0表示自营")
    @TableField("shop_id")
    private Long shopId;

    @Schema(title = "spu_id")
    @TableField("spu_id")
    private Long spuId;


}
