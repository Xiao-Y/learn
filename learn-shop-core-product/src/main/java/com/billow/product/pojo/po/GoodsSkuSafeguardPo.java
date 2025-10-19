package com.billow.product.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sku增值保障
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("pms_goods_sku_safeguard")
@Schema(title = "GoodsSkuSafeguardPo对象", description="sku增值保障")
public class GoodsSkuSafeguardPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "sku_id")
    @Column("sku_id")
    private Long skuId;

    @Schema(title = "safeguard_id")
    @Column("safeguard_id")
    private Long safeguardId;


}
