package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品分类
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_category")
@ApiModel(value="CategoryPo对象", description="商品分类")
public class CategoryPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "分类名")
    private String categoryName;

    @ApiModelProperty(value = "排序")
    private Integer sortOrder;


}
