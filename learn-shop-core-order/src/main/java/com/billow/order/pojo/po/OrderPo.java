package com.billow.order.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table("oms_order")
@Schema(title = "OrderPo对象", description="")
public class OrderPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "会员id")
    @Column("member_id")
    private Long memberId;

    @Schema(title = "优惠券id")
    @Column("coupon_id")
    private Long couponId;

    @Schema(title = "订单编号")
    @Column("order_sn")
    private String orderSn;

    @Schema(title = "用户帐号")
    @Column("member_username")
    private String memberUsername;

    @Schema(title = "订单总金额")
    @Column("total_amount")
    private BigDecimal totalAmount;

    @Schema(title = "应付金额（实际支付金额）")
    @Column("pay_amount")
    private BigDecimal payAmount;

    @Schema(title = "运费金额")
    @Column("freight_amount")
    private BigDecimal freightAmount;

    @Schema(title = "促销优化金额（促销价、满减、阶梯价）")
    @Column("promotion_amount")
    private BigDecimal promotionAmount;

    @Schema(title = "积分抵扣金额")
    @Column("integration_amount")
    private BigDecimal integrationAmount;

    @Schema(title = "优惠券抵扣金额")
    @Column("coupon_amount")
    private BigDecimal couponAmount;

    @Schema(title = "管理员后台调整订单使用的折扣金额")
    @Column("discount_amount")
    private BigDecimal discountAmount;

    @Schema(title = "支付方式：0->未支付；1->支付宝；2->微信")
    @Column("pay_type")
    private Integer payType;

    @Schema(title = "订单来源：0->PC订单；1->app订单")
    @Column("source_type")
    private Integer sourceType;

    @Schema(title = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    @Column("status")
    private Integer status;

    @Schema(title = "订单类型：0->正常订单；1->秒杀订单")
    @Column("order_type")
    private Integer orderType;

    @Schema(title = "物流公司(配送方式)")
    @Column("delivery_company")
    private String deliveryCompany;

    @Schema(title = "物流单号")
    @Column("delivery_sn")
    private String deliverySn;

    @Schema(title = "自动确认时间（天）")
    @Column("auto_confirm_day")
    private Integer autoConfirmDay;

    @Schema(title = "可以获得的积分")
    @Column("integration")
    private Integer integration;

    @Schema(title = "可以活动的成长值")
    @Column("growth")
    private Integer growth;

    @Schema(title = "活动信息")
    @Column("promotion_info")
    private String promotionInfo;

    @Schema(title = "发票类型：0->不开发票；1->电子发票；2->纸质发票")
    @Column("bill_type")
    private Integer billType;

    @Schema(title = "发票抬头")
    @Column("bill_header")
    private String billHeader;

    @Schema(title = "发票内容")
    @Column("bill_content")
    private String billContent;

    @Schema(title = "收票人电话")
    @Column("bill_receiver_phone")
    private String billReceiverPhone;

    @Schema(title = "收票人邮箱")
    @Column("bill_receiver_email")
    private String billReceiverEmail;

    @Schema(title = "收货人姓名")
    @Column("receiver_name")
    private String receiverName;

    @Schema(title = "收货人电话")
    @Column("receiver_phone")
    private String receiverPhone;

    @Schema(title = "收货人邮编")
    @Column("receiver_post_code")
    private String receiverPostCode;

    @Schema(title = "省份/直辖市")
    @Column("receiver_province")
    private String receiverProvince;

    @Schema(title = "城市")
    @Column("receiver_city")
    private String receiverCity;

    @Schema(title = "区")
    @Column("receiver_region")
    private String receiverRegion;

    @Schema(title = "详细地址")
    @Column("receiver_detail_address")
    private String receiverDetailAddress;

    @Schema(title = "订单备注")
    @Column("note")
    private String note;

    @Schema(title = "确认收货状态：0->未确认；1->已确认")
    @Column("confirm_status")
    private Integer confirmStatus;

    @Schema(title = "删除状态：0->未删除；1->已删除")
    @Column("delete_status")
    private Integer deleteStatus;

    @Schema(title = "下单时使用的积分")
    @Column("use_integration")
    private Integer useIntegration;

    @Schema(title = "支付时间")
    @Column("payment_time")
    private LocalDateTime paymentTime;

    @Schema(title = "发货时间")
    @Column("delivery_time")
    private LocalDateTime deliveryTime;

    @Schema(title = "确认收货时间")
    @Column("receive_time")
    private LocalDateTime receiveTime;

    @Schema(title = "评价时间")
    @Column("comment_time")
    private LocalDateTime commentTime;

    @Schema(title = "提交时间")
    @Column("submit_time")
    private LocalDateTime submitTime;


}
