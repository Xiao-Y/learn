package com.billow.product.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table("pms_goods_operate_log")
@Schema(title = "GoodsOperateLogPo对象", description = "商品操作记录表，用于记录商品操作记录")
public class GoodsOperateLogPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "商品id")
    @Column("spu_id")
    private Long spuId;

    @Schema(title = "改变前价格")
    @Column("price_old")
    private BigDecimal priceOld;

    @Schema(title = "改变后价格")
    @Column("price_new")
    private BigDecimal priceNew;

    @Schema(title = "操作人")
    @Column("operate_man")
    private String operateMan;


}
