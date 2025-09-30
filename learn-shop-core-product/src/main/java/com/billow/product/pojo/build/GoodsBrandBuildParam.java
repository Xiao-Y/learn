package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 品牌表 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsBrandBuildParam extends BasePo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "首字母")
    private String firstLetter;

    @Schema(title = "品牌名称")
    private String brandName;

    @Schema(title = "分类排序")
    private Long brandSort;

    @Schema(title = "是否显示")
    private Integer showStatus;

    @Schema(title = "产品数量")
    private Integer productCount;

    @Schema(title = "品牌logo")
    private String logo;

    @Schema(title = "专区大图")
    private String bigPic;

    @Schema(title = "品牌故事")
    private String brandStory;


}
