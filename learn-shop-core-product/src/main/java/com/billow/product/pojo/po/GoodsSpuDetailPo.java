package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_goods_spu_detail")
@ApiModel(value="GoodsSpuDetailPo对象", description="")
public class GoodsSpuDetailPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableId(value = "spu_id", type = IdType.ID_WORKER)
    private Long spuId;

    @ApiModelProperty(value = "商品描述信息")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "全部规格参数数据")
    @TableField("specifications")
    private String specifications;

    @ApiModelProperty(value = "特有规格参数及可选值信息，json格式")
    @TableField("spec_template")
    private String specTemplate;

    @ApiModelProperty(value = "包装清单")
    @TableField("packing_list")
    private String packingList;

    @ApiModelProperty(value = "售后服务")
    @TableField("after_service")
    private String afterService;


}
