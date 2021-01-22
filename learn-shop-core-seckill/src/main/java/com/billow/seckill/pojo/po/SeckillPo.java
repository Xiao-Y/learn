package com.billow.seckill.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 秒杀库存表
 * </p>
 *
 * @author billow
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sk_seckill")
@ApiModel(value="SeckillPo对象", description="秒杀库存表")
public class SeckillPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku编号,唯一")
    private String skuNo;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "库存数量")
    private Integer stock;

    @ApiModelProperty(value = "秒杀开始时间")
    private Date startTime;

    @ApiModelProperty(value = "秒杀结束时间")
    private Date endTime;

    @ApiModelProperty(value = "商品说明")
    private String remarks;


}
