package com.billow.order.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_order_return_reason")
@Schema(title = "OrderReturnReasonPo对象", description="")
public class OrderReturnReasonPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "退货类型")
    @TableField("name")
    private String name;

    @TableField("sort")
    private Integer sort;

    @Schema(title = "状态：0->不启用；1->启用")
    @TableField("status")
    private Integer status;


}
