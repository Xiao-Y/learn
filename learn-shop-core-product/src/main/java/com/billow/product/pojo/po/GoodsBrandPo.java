package com.billow.product.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
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
@Table("pms_goods_brand")
@Schema(title = "GoodsBrandPo对象", description="品牌表")
public class GoodsBrandPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "首字母")
    @Column("first_letter")
    private String firstLetter;

    @Schema(title = "品牌名称")
    @Column("brand_name")
    private String brandName;

    @Schema(title = "分类排序")
    @Column("brand_sort")
    private Long brandSort;

    @Schema(title = "是否显示")
    @Column("show_status")
    private Integer showStatus;

    @Schema(title = "产品数量")
    @Column("product_count")
    private Integer productCount;

    @Schema(title = "品牌logo")
    @Column("logo")
    private String logo;

    @Schema(title = "专区大图")
    @Column("big_pic")
    private String bigPic;

    @Schema(title = "品牌故事")
    @Column("brand_story")
    private String brandStory;


}
