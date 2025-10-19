package com.billow.cart.pojo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table("cart_info")
@Schema(title = "CartInfo对象", description = "购物车信息表")
public class CartInfo extends BasePo {

    @Schema(title = "租户ID")
    @Column("tenant_id")
    private Long tenantId;

    @Schema(title = "用户ID")
    @Column("user_id")
    private Long userId;

    @Schema(title = "商品总数量")
    @Column("total_quantity")
    private Integer totalQuantity;

    @Schema(title = "商品总金额")
    @Column("total_amount")
    private BigDecimal totalAmount;

    @Schema(title = "已选商品数量")
    @Column("selected_quantity")
    private Integer selectedQuantity;

    @Schema(title = "已选商品金额")
    @Column("selected_amount")
    private BigDecimal selectedAmount;

    @Schema(title = "状态：1-正常，2-已清空，3-已下单")
    @Column("status")
    private Integer status;

    @Schema(title = "乐观锁版本号")
    @Column("version")
    private Integer version;

    @Schema(title = "备注")
    @Column("remark")
    private String remark;
} 