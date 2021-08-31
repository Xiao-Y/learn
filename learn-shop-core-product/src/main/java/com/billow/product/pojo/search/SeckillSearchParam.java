package com.billow.product.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SeckillSearchParam extends BasePage implements Serializable {

}
