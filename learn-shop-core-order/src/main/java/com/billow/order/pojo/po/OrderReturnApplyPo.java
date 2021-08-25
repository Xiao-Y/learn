package com.billow.order.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_order_return_apply")
@ApiModel(value="OrderReturnApplyPo对象", description="")
public class OrderReturnApplyPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "收货地址表id")
    @TableField("company_address_id")
    private Long companyAddressId;

    @ApiModelProperty(value = "退货商品id")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty(value = "申请时间")
    @TableField("apply_time")
    private LocalDateTime applyTime;

    @ApiModelProperty(value = "会员用户名")
    @TableField("member_username")
    private String memberUsername;

    @ApiModelProperty(value = "退款金额")
    @TableField("return_amount")
    private BigDecimal returnAmount;

    @ApiModelProperty(value = "退货人姓名")
    @TableField("return_name")
    private String returnName;

    @ApiModelProperty(value = "退货人电话")
    @TableField("return_phone")
    private String returnPhone;

    @ApiModelProperty(value = "申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "处理时间")
    @TableField("handle_time")
    private LocalDateTime handleTime;

    @ApiModelProperty(value = "商品图片")
    @TableField("product_pic")
    private String productPic;

    @ApiModelProperty(value = "商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty(value = "商品品牌")
    @TableField("product_brand")
    private String productBrand;

    @ApiModelProperty(value = "商品销售属性：颜色：红色；尺码：xl;")
    @TableField("product_attr")
    private String productAttr;

    @ApiModelProperty(value = "退货数量")
    @TableField("product_count")
    private Integer productCount;

    @ApiModelProperty(value = "商品单价")
    @TableField("product_price")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "商品实际支付单价")
    @TableField("product_real_price")
    private BigDecimal productRealPrice;

    @ApiModelProperty(value = "原因")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "凭证图片，以逗号隔开")
    @TableField("proof_pics")
    private String proofPics;

    @ApiModelProperty(value = "处理备注")
    @TableField("handle_note")
    private String handleNote;

    @ApiModelProperty(value = "处理人员")
    @TableField("handle_man")
    private String handleMan;

    @ApiModelProperty(value = "收货人")
    @TableField("receive_man")
    private String receiveMan;

    @ApiModelProperty(value = "收货时间")
    @TableField("receive_time")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "收货备注")
    @TableField("receive_note")
    private String receiveNote;


}
