package com.billow.order.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀库存表
 * </p>
 *
 * @author billow
 * @since 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("sk_seckill")
@Schema(title = "SeckillPo对象", description="秒杀库存表")
public class SeckillPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "sku编号,唯一")
    @Column("sku_no")
    private String skuNo;

    @Schema(title = "商品名称")
    @Column("goods_name")
    private String goodsName;

    @Schema(title = "库存数量")
    @Column("stock")
    private Integer stock;

    @Schema(title = "秒杀开始时间")
    @Column("start_time")
    private LocalDateTime startTime;

    @Schema(title = "秒杀结束时间")
    @Column("end_time")
    private LocalDateTime endTime;

    @Schema(title = "支付过期时间（单位：分钟）")
    @Column("payment_exp")
    private Long paymentExp;

    @Schema(title = "是否已经加载过")
    @Column("is_load_cache")
    private Boolean loadCache;

    @Schema(title = "商品说明")
    @Column("remarks")
    private String remarks;


}
