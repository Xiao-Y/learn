package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品分类和品牌的中间表，两者是多对多关系
 * </p>
 *
 * @author billow
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_category_brand")
@ApiModel(value="GoodsCategoryBrandPo对象", description="商品分类和品牌的中间表，两者是多对多关系")
public class GoodsCategoryBrandPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品类目id")
    @TableId(value = "category_id", type = IdType.ID_WORKER)
    private Long categoryId;

    @ApiModelProperty(value = "品牌id")
    @TableField("brand_id")
    private Long brandId;


}
