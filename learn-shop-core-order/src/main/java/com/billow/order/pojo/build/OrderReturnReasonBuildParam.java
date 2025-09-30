package com.billow.order.pojo.build;

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
 * @version v2.0
 * @since 2021-08-23
 */
@Data
@Accessors(chain = true)
public class OrderReturnReasonBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "退货类型")
    private String name;

    private Integer sort;

    @Schema(title = "状态：0->不启用；1->启用")
    private Integer status;


}
