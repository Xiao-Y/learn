package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 商品操作记录表，用于记录商品操作记录
 *
 *
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_goods_operate_log")
@ApiModel(value = "GoodsOperateLogPo对象", description = "商品操作记录表，用于记录商品操作记录")
public class GoodsOperateLogPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "改变前价格")
    @TableField("price_old")
    private BigDecimal priceOld;

    @ApiModelProperty(value = "改变后价格")
    @TableField("price_new")
    private BigDecimal priceNew;

    @ApiModelProperty(value = "操作人")
    @TableField("operate_man")
    private String operateMan;


}
