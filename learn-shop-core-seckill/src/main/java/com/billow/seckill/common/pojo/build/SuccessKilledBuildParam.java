package com.billow.seckill.common.pojo.build;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 秒杀成功明细表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-13
 */
@Data
@Accessors(chain = true)
public class SuccessKilledBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户code")
    private String usercode;

    @ApiModelProperty(value = "秒杀商品ID")
    private Long seckillId;

    @ApiModelProperty(value = "秒杀状态标识:-0:无效 1:成功 2:已付款 3:已发货")
    private Integer killState;


}
