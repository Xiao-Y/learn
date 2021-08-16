package com.billow.seckill.pojo.vo;


import com.billow.seckill.pojo.po.SuccessKilledPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 秒杀成功明细表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SuccessKilledVo extends SuccessKilledPo implements Serializable {

}
