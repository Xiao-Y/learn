package com.billow.product.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 分类表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsCategoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "分类排序")
    private Long categorySort;

    @ApiModelProperty(value = "父类目id,顶级类目填0")
    private Long parentId;

    @ApiModelProperty(value = "是否为父节点，0为否，1为是")
    private Boolean parent;


}
