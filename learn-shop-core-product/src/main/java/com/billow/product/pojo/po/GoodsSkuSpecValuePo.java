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
 * sku规格值
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("pms_goods_sku_spec_value")
@Schema(title = "GoodsSkuSpecValuePo对象", description="sku规格值")
public class GoodsSkuSpecValuePo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "sku_id")
    @Column("sku_id")
    private Long skuId;

    @Schema(title = "spu_id")
    @Column("spu_id")
    private Long spuId;

    @Schema(title = "规格id(冗余)")
    @Column("spec_key_id")
    private Long specKeyId;

    @Schema(title = "规格值id")
    @Column("spec_value_id")
    private Long specValueId;

    @Schema(title = "规格值排序")
    @Column("sku_spec_sort")
    private Long skuSpecSort;


}
