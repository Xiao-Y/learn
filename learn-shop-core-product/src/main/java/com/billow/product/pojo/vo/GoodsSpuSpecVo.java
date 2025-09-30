package com.billow.product.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * spu规格表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsSpuSpecVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "spu_id")
    private Long spuId;

    @Schema(title = "spec_key_id")
    private Long specKeyId;


}
