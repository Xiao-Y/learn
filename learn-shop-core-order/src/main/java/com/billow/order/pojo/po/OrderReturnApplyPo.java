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
@Table("oms_order_return_apply")
@Schema(title = "OrderReturnApplyPo对象", description="")
public class OrderReturnApplyPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "订单id")
    @Column("order_id")
    private Long orderId;

    @Schema(title = "收货地址表id")
    @Column("company_address_id")
    private Long companyAddressId;

    @Schema(title = "退货商品id")
    @Column("product_id")
    private Long productId;

    @Schema(title = "订单编号")
    @Column("order_sn")
    private String orderSn;

    @Schema(title = "申请时间")
    @Column("apply_time")
    private LocalDateTime applyTime;

    @Schema(title = "会员用户名")
    @Column("member_username")
    private String memberUsername;

    @Schema(title = "退款金额")
    @Column("return_amount")
    private BigDecimal returnAmount;

    @Schema(title = "退货人姓名")
    @Column("return_name")
    private String returnName;

    @Schema(title = "退货人电话")
    @Column("return_phone")
    private String returnPhone;

    @Schema(title = "申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝")
    @Column("status")
    private Integer status;

    @Schema(title = "处理时间")
    @Column("handle_time")
    private LocalDateTime handleTime;

    @Schema(title = "商品图片")
    @Column("product_pic")
    private String productPic;

    @Schema(title = "商品名称")
    @Column("product_name")
    private String productName;

    @Schema(title = "商品品牌")
    @Column("product_brand")
    private String productBrand;

    @Schema(title = "商品销售属性：颜色：红色；尺码：xl;")
    @Column("product_attr")
    private String productAttr;

    @Schema(title = "退货数量")
    @Column("product_count")
    private Integer productCount;

    @Schema(title = "商品单价")
    @Column("product_price")
    private BigDecimal productPrice;

    @Schema(title = "商品实际支付单价")
    @Column("product_real_price")
    private BigDecimal productRealPrice;

    @Schema(title = "原因")
    @Column("reason")
    private String reason;

    @Schema(title = "描述")
    @Column("description")
    private String description;

    @Schema(title = "凭证图片，以逗号隔开")
    @Column("proof_pics")
    private String proofPics;

    @Schema(title = "处理备注")
    @Column("handle_note")
    private String handleNote;

    @Schema(title = "处理人员")
    @Column("handle_man")
    private String handleMan;

    @Schema(title = "收货人")
    @Column("receive_man")
    private String receiveMan;

    @Schema(title = "收货时间")
    @Column("receive_time")
    private LocalDateTime receiveTime;

    @Schema(title = "收货备注")
    @Column("receive_note")
    private String receiveNote;


}
