package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。
 * </p>
 *
 * @author billow
 * @since 2021-08-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sms_seckill_log")
@Schema(title = "SeckillLogPo对象", description="限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。")
public class SeckillLogPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "会员id")
    @TableField("member_id")
    private Integer memberId;

    @Schema(title = "商品id")
    @TableField("product_id")
    private Long productId;

    @Schema(title = "会员电话")
    @TableField("member_phone")
    private String memberPhone;

    @Schema(title = "商品名称")
    @TableField("product_name")
    private String productName;

    @Schema(title = "会员订阅时间")
    @TableField("subscribe_time")
    private LocalDateTime subscribeTime;

    @Schema(title = "发送时间")
    @TableField("send_time")
    private LocalDateTime sendTime;


}
