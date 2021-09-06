package com.billow.product.interfaces.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 品牌表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsBrandVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "首字母")
    private String firstLetter;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "分类排序")
    private Long brandSort;

    @ApiModelProperty(value = "是否显示")
    private Integer showStatus;

    @ApiModelProperty(value = "产品数量")
    private Integer productCount;

    @ApiModelProperty(value = "品牌logo")
    private String logo;

    @ApiModelProperty(value = "专区大图")
    private String bigPic;

    @ApiModelProperty(value = "品牌故事")
    private String brandStory;


}
