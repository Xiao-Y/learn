package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * sku表（stock keeping uint 库存量单位） 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsSkuBuildParam extends BasePo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku编号,唯一")
    private String skuNo;

    @ApiModelProperty(value = "sku名称(冗余spu_name)")
    private String skuName;

    @ApiModelProperty(value = "售价")
    private Integer price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "锁定库存")
    private Integer lockStock;

    @ApiModelProperty(value = "预警库存")
    private Integer lowStock;

    @ApiModelProperty(value = "展示图片")
    private String pic;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "商铺id,为0表示自营")
    private Long shopId;

    @ApiModelProperty(value = "spu_id")
    private Long spuId;


}
