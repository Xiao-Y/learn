package com.billow.product.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_category")
@ApiModel(value="GoodsCategoryPo对象", description="分类表")
public class GoodsCategoryPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类名称")
    @TableField("category_name")
    private String categoryName;

    @ApiModelProperty(value = "分类排序")
    @TableField("category_sort")
    private Long categorySort;

    @ApiModelProperty(value = "父类目id,顶级类目填0")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "是否为父节点，0为否，1为是")
    @TableField("is_parent")
    private Boolean isParent;


}
