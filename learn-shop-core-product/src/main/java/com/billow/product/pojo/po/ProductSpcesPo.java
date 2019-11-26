package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 商品规格
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_product_spces")
@ApiModel(value="ProductSpcesPo对象", description="商品规格")
public class ProductSpcesPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private Integer productId;

    @ApiModelProperty(value = "商品规格")
    private String productSpces;

    @ApiModelProperty(value = "规格序号")
    private Integer spcesSeq;

    @ApiModelProperty(value = "当前 sku 组合对应的库存")
    private Integer stockNum;

    @ApiModelProperty(value = "当前 sku 组合对应价格（单位分）")
    private BigDecimal price;

    @ApiModelProperty(value = "规格类目图片")
    @TableField("imgUrl")
    private String imgUrl;

    @ApiModelProperty(value = "用于预览显示的规格类目图片")
    @TableField("previewImgUrl")
    private String previewImgUrl;


}
