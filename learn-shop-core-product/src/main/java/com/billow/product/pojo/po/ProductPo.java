package com.billow.product.pojo.po;

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
 * 商品信息
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_product")
@ApiModel(value="ProductPo对象", description="商品信息")
public class ProductPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类主键")
    private String categoryId;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "默认商品 sku 缩略图")
    private String picture;

    @ApiModelProperty(value = "默认价格（单位元）")
    private BigDecimal price;

    @ApiModelProperty(value = "商品总库存")
    private Integer stockNum;

    @ApiModelProperty(value = "是否无规格商品")
    private Boolean noneSku;

    @ApiModelProperty(value = "是否隐藏剩余库存")
    private Boolean hideStock;


}
