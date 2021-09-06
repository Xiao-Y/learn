package com.billow.product.pojo.build;

import io.swagger.annotations.ApiModelProperty;
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
public class GoodsVertifyRecordBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private Long spuId;

    @ApiModelProperty(value = "审核人")
    private String vertifyMan;

    @ApiModelProperty(value = "审核后的状态：0->未通过；2->已通过")
    private Integer status;

    @ApiModelProperty(value = "反馈详情")
    private String detail;


}
