package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品审核记录表，用于记录商品审核记录 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsVertifyRecordBuildParam extends BasePo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "商品id")
    private Long spuId;

    @Schema(title = "审核人")
    private String vertifyMan;

    @Schema(title = "审核后的状态：0->未通过；2->已通过")
    private Integer status;

    @Schema(title = "反馈详情")
    private String detail;


}
