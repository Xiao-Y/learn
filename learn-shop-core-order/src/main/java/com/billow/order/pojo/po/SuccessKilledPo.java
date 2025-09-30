package com.billow.order.pojo.po;

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
 * 秒杀成功明细表
 * </p>
 *
 * @author billow
 * @since 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sk_success_killed")
@Schema(title = "SuccessKilledPo对象", description="秒杀成功明细表")
public class SuccessKilledPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "用户code")
    @TableField("usercode")
    private String usercode;

    @Schema(title = "秒杀商品ID")
    @TableField("seckill_id")
    private Long seckillId;

    @Schema(title = "秒杀状态标识:-0:无效 1:成功 2:已付款 3:已发货")
    @TableField("kill_state")
    private Integer killState;

    @Schema(title = "订单过期时间")
    @TableField("expire")
    private LocalDateTime expire;


}
