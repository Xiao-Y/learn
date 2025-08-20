package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "规格编号")
    private String specNo;

    @Schema(title = "规格名称")
    private String specName;

    @Schema(title = "规格排序")
    private Long keySort;

    @Schema(title = "分类id")
    private Long categoryId;


}
