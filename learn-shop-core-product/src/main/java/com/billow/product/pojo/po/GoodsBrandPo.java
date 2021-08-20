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
 * 品牌表
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_brand")
@ApiModel(value="GoodsBrandPo对象", description="品牌表")
public class GoodsBrandPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品牌名称")
    @TableField("brand_name")
    private String brandName;

    @ApiModelProperty(value = "品牌的首字母")
    @TableField("letter")
    private String letter;

    @ApiModelProperty(value = "品牌图片地址")
    @TableField("image")
    private String image;


}
