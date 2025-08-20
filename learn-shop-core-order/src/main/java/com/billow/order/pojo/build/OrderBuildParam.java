package com.billow.order.pojo.build;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@Data
@Accessors(chain = true)
public class OrderBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "会员id")
    private Long memberId;

    @Schema(title = "优惠券id")
    private Long couponId;

    @Schema(title = "订单编号")
    private String orderSn;

    @Schema(title = "用户帐号")
    private String memberUsername;

    @Schema(title = "订单总金额")
    private BigDecimal totalAmount;

    @Schema(title = "应付金额（实际支付金额）")
    private BigDecimal payAmount;

    @Schema(title = "运费金额")
    private BigDecimal freightAmount;

    @Schema(title = "促销优化金额（促销价、满减、阶梯价）")
    private BigDecimal promotionAmount;

    @Schema(title = "积分抵扣金额")
    private BigDecimal integrationAmount;

    @Schema(title = "优惠券抵扣金额")
    private BigDecimal couponAmount;

    @Schema(title = "管理员后台调整订单使用的折扣金额")
    private BigDecimal discountAmount;

    @Schema(title = "支付方式：0->未支付；1->支付宝；2->微信")
    private Integer payType;

    @Schema(title = "订单来源：0->PC订单；1->app订单")
    private Integer sourceType;

    @Schema(title = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;

    @Schema(title = "订单类型：0->正常订单；1->秒杀订单")
    private Integer orderType;

    @Schema(title = "物流公司(配送方式)")
    private String deliveryCompany;

    @Schema(title = "物流单号")
    private String deliverySn;

    @Schema(title = "自动确认时间（天）")
    private Integer autoConfirmDay;

    @Schema(title = "可以获得的积分")
    private Integer integration;

    @Schema(title = "可以活动的成长值")
    private Integer growth;

    @Schema(title = "活动信息")
    private String promotionInfo;

    @Schema(title = "发票类型：0->不开发票；1->电子发票；2->纸质发票")
    private Integer billType;

    @Schema(title = "发票抬头")
    private String billHeader;

    @Schema(title = "发票内容")
    private String billContent;

    @Schema(title = "收票人电话")
    private String billReceiverPhone;

    @Schema(title = "收票人邮箱")
    private String billReceiverEmail;

    @Schema(title = "收货人姓名")
    private String receiverName;

    @Schema(title = "收货人电话")
    private String receiverPhone;

    @Schema(title = "收货人邮编")
    private String receiverPostCode;

    @Schema(title = "省份/直辖市")
    private String receiverProvince;

    @Schema(title = "城市")
    private String receiverCity;

    @Schema(title = "区")
    private String receiverRegion;

    @Schema(title = "详细地址")
    private String receiverDetailAddress;

    @Schema(title = "订单备注")
    private String note;

    @Schema(title = "确认收货状态：0->未确认；1->已确认")
    private Integer confirmStatus;

    @Schema(title = "删除状态：0->未删除；1->已删除")
    private Integer deleteStatus;

    @Schema(title = "下单时使用的积分")
    private Integer useIntegration;

    @Schema(title = "支付时间")
    private LocalDateTime paymentTime;

    @Schema(title = "发货时间")
    private LocalDateTime deliveryTime;

    @Schema(title = "确认收货时间")
    private LocalDateTime receiveTime;

    @Schema(title = "评价时间")
    private LocalDateTime commentTime;

    @Schema(title = "提交时间")
    private LocalDateTime submitTime;


}
