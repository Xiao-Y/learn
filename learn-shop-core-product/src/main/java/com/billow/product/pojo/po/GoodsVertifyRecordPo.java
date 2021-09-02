package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="GoodsVertifyRecordPo对象", description="商品审核记录表，用于记录商品审核记录")
public class GoodsVertifyRecordPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "审核人")
    @TableField("vertify_man")
    private String vertifyMan;

    @ApiModelProperty(value = "审核后的状态：0->未通过；2->已通过")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "反馈详情")
    @TableField("detail")
    private String detail;


}
