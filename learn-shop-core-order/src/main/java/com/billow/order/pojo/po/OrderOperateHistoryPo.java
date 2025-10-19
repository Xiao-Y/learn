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
@Table("oms_order_operate_history")
@Schema(title = "OrderOperateHistoryPo对象", description="")
public class OrderOperateHistoryPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "订单id")
    @Column("order_id")
    private Long orderId;

    @Schema(title = "操作人：用户；系统；后台管理员")
    @Column("operate_man")
    private String operateMan;

    @Schema(title = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    @Column("order_status")
    private Integer orderStatus;

    @Schema(title = "备注")
    @Column("note")
    private String note;


}
