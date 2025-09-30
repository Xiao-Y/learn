package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "sku编号,唯一")
    private String skuNo;

    @Schema(title = "sku名称(冗余spu_name)")
    private String skuName;

    @Schema(title = "售价")
    private Integer price;

    @Schema(title = "库存")
    private Integer stock;

    @Schema(title = "锁定库存")
    private Integer lockStock;

    @Schema(title = "预警库存")
    private Integer lowStock;

    @Schema(title = "展示图片")
    private String pic;

    @Schema(title = "销量")
    private Integer sale;

    @Schema(title = "商铺id,为0表示自营")
    private Long shopId;

    @Schema(title = "spu_id")
    private Long spuId;


}
