package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSpecKeyPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 规格表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Data
@Accessors(chain = true)
public class GoodsSpecKeyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "规格编号")
    private String specNo;

    @ApiModelProperty(value = "规格名称")
    private String specName;

    @ApiModelProperty(value = "规格排序")
    private Long keySort;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    private List<GoodsSpecValueVo> goodsSpecValueVos;
}
