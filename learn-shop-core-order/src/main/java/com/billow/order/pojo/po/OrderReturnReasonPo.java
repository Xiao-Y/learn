package com.billow.order.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
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
@Table("oms_order_return_reason")
@Schema(title = "OrderReturnReasonPo对象", description="")
public class OrderReturnReasonPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "退货类型")
    @Column("name")
    private String name;

    @Column("sort")
    private Integer sort;

    @Schema(title = "状态：0->不启用；1->启用")
    @Column("status")
    private Integer status;


}
