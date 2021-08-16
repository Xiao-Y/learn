package com.billow.seckill.common.pojo.vo;


import com.billow.seckill.common.pojo.po.SeckillPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 秒杀库存表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SeckillVo extends SeckillPo implements Serializable {

}
