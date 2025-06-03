package com.billow.cart.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 购物车实体
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cart")
@ApiModel(value="CartPo对象", description="购物车")
public class CartPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "SKU ID")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "商品数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty(value = "是否选中")
    @TableField("selected")
    private Boolean selected;
} 