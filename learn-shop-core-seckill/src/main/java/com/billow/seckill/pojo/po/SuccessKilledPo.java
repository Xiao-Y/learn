package com.billow.seckill.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 秒杀成功明细表
 * </p>
 *
 * @author billow
 * @since 2021-01-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sk_success_killed")
@ApiModel(value="SuccessKilledPo对象", description="秒杀成功明细表")
public class SuccessKilledPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户code")
    private String usercode;

    @ApiModelProperty(value = "秒杀商品ID")
    private Long seckillId;

    @ApiModelProperty(value = "秒杀状态标识:-0:无效 1:成功 2:已付款 3:已发货")
    private Integer killState;


}
