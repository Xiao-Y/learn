package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品审核记录表，用于记录商品审核记录
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_goods_vertify_record")
@Schema(title = "GoodsVertifyRecordPo对象", description="商品审核记录表，用于记录商品审核记录")
public class GoodsVertifyRecordPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "商品id")
    @TableField("spu_id")
    private Long spuId;

    @Schema(title = "审核人")
    @TableField("vertify_man")
    private String vertifyMan;

    @Schema(title = "审核后的状态：0->未通过；2->已通过")
    @TableField("status")
    private Integer status;

    @Schema(title = "反馈详情")
    @TableField("detail")
    private String detail;


}
