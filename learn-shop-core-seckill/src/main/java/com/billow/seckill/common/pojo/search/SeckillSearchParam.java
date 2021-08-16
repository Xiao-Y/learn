package com.billow.seckill.common.pojo.search;

import com.billow.mybatis.pojo.BasePage;
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
 * @since 2021-08-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SeckillSearchParam extends BasePage implements Serializable {

}
