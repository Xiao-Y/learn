package com.billow.product.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * sku增值保障 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsSkuSafeguardVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "sku_id")
    private Long skuId;

    @Schema(title = "safeguard_id")
    private Long safeguardId;


}
