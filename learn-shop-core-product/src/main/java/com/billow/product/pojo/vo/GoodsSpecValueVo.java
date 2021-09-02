package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSpecValuePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 规格值表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Data
@Accessors(chain = true)
public class GoodsSpecValueVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格id")
    private Long specKeyId;

    @ApiModelProperty(value = "规格值")
    private String specValue;

    @ApiModelProperty(value = "规格排序")
    private Long valueSort;

    // 页面只读
    private boolean readonly = true;
}
