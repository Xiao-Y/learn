package com.billow.order.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("sk_seckill")
@ApiModel(value="SeckillPo对象", description="秒杀库存表")
public class SeckillPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku编号,唯一")
    @TableField("sku_no")
    private String skuNo;

    @ApiModelProperty(value = "商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty(value = "库存数量")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty(value = "秒杀开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "秒杀结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "支付过期时间（单位：分钟）")
    @TableField("payment_exp")
    private Long paymentExp;

    @ApiModelProperty(value = "是否已经加载过")
    @TableField("is_load_cache")
    private Boolean loadCache;

    @ApiModelProperty(value = "商品说明")
    @TableField("remarks")
    private String remarks;


}
