package com.billow.product.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SeckillSessionSearchParam extends BasePage implements Serializable {

}
