package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 规格表 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsSpecKeyBuildParam extends BasePo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格编号")
    private String specNo;

    @ApiModelProperty(value = "规格名称")
    private String specName;

    @ApiModelProperty(value = "规格排序")
    private Long keySort;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;


}
