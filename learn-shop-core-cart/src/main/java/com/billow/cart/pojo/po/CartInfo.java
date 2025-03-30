package com.billow.cart.pojo.entity;

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
 * 购物车信息表
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("cart_info")
@ApiModel(value = "CartInfo对象", description = "购物车信息表")
public class CartInfo extends BasePo {

    @ApiModelProperty("租户ID")
    @TableField("tenant_id")
    private Long tenantId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("商品总数量")
    @TableField("total_quantity")
    private Integer totalQuantity;

    @ApiModelProperty("商品总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty("已选商品数量")
    @TableField("selected_quantity")
    private Integer selectedQuantity;

    @ApiModelProperty("已选商品金额")
    @TableField("selected_amount")
    private BigDecimal selectedAmount;

    @ApiModelProperty("状态：1-正常，2-已清空，3-已下单")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("乐观锁版本号")
    @TableField("version")
    private Integer version;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
} 