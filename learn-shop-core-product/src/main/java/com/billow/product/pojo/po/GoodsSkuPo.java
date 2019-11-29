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
 * sku表
 * </p>
 *
 * @author billow
 * @since 2019-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_sku")
@ApiModel(value="GoodsSkuPo对象", description="sku表")
public class GoodsSkuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku编号,唯一")
    private String skuNo;

    @ApiModelProperty(value = "sku名称(冗余spu_name)")
    private String skuName;

    @ApiModelProperty(value = "售价")
    private Integer price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "商铺id,为0表示自营")
    private String shopId;

    @ApiModelProperty(value = "spu_id")
    private String spuId;


}
