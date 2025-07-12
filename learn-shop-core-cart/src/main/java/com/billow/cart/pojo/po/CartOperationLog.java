package com.billow.cart.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 购物车操作日志表
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("cart_operation_log")
@ApiModel(value = "CartOperationLog对象", description = "购物车操作日志表")
public class CartOperationLog extends BasePo {

    @ApiModelProperty("租户ID")
    @TableField("tenant_id")
    private Long tenantId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("购物车ID")
    @TableField("cart_id")
    private Long cartId;

    @ApiModelProperty("购物车商品ID")
    @TableField("cart_item_id")
    private Long cartItemId;

    @ApiModelProperty("操作类型：1-添加商品，2-更新数量，3-删除商品，4-清空购物车，5-选中商品，6-取消选中")
    @TableField("operation_type")
    private Integer operationType;

    @ApiModelProperty("操作描述")
    @TableField("operation_desc")
    private String operationDesc;

    @ApiModelProperty("操作结果：0-失败，1-成功")
    @TableField("operation_result")
    private Boolean operationResult;

    @ApiModelProperty("失败原因")
    @TableField("fail_reason")
    private String failReason;

    @ApiModelProperty("操作时间")
    @TableField("operation_time")
    private Date operationTime;
} 