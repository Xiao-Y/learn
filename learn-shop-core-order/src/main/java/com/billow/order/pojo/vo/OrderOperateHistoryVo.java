package com.billow.order.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-23
 */
@Data
@Accessors(chain = true)
public class OrderOperateHistoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "订单id")
    private Long orderId;

    @Schema(title = "操作人：用户；系统；后台管理员")
    private String operateMan;

    @Schema(title = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer orderStatus;

    @Schema(title = "备注")
    private String note;


}
