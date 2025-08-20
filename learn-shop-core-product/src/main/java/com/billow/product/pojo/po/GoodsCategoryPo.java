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
 * 分类表
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_goods_category")
@Schema(title = "GoodsCategoryPo对象", description="分类表")
public class GoodsCategoryPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "分类名称")
    @TableField("category_name")
    private String categoryName;

    @Schema(title = "分类排序")
    @TableField("category_sort")
    private Long categorySort;

    @Schema(title = "父类目id,顶级类目填0")
    @TableField("parent_id")
    private Long parentId;

    @Schema(title = "是否为父节点，0为否，1为是")
    @TableField("is_parent")
    private Boolean parent;


}
