package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSpecKeyPo;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "规格编号")
    private String specNo;

    @Schema(title = "规格名称")
    private String specName;

    @Schema(title = "规格排序")
    private Long keySort;

    @Schema(title = "分类id")
    private Long categoryId;

    private List<GoodsSpecValueVo> goodsSpecValueVos;
}
