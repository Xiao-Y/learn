package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * sku规格值 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsSkuSpecValueBuildParam extends BasePo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "sku_id")
    private Long skuId;

    @Schema(title = "商品id")
    private Long spuId;

    @Schema(title = "规格id(冗余)")
    private Long specKeyId;

    @Schema(title = "规格值id")
    private Long specValueId;

    @Schema(title = "规格值排序")
    private Long skuSpecSort;


}
