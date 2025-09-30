package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_goods_brand")
@Schema(title = "GoodsBrandPo对象", description="品牌表")
public class GoodsBrandPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "首字母")
    @TableField("first_letter")
    private String firstLetter;

    @Schema(title = "品牌名称")
    @TableField("brand_name")
    private String brandName;

    @Schema(title = "分类排序")
    @TableField("brand_sort")
    private Long brandSort;

    @Schema(title = "是否显示")
    @TableField("show_status")
    private Integer showStatus;

    @Schema(title = "产品数量")
    @TableField("product_count")
    private Integer productCount;

    @Schema(title = "品牌logo")
    @TableField("logo")
    private String logo;

    @Schema(title = "专区大图")
    @TableField("big_pic")
    private String bigPic;

    @Schema(title = "品牌故事")
    @TableField("brand_story")
    private String brandStory;


}
