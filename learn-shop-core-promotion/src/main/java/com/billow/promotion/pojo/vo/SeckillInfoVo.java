package com.billow.promotion.pojo.vo;

import com.billow.promotion.pojo.cache.SeckillCacheDto;
import com.billow.promotion.pojo.cache.SeckillProductCacheDto;
import com.billow.promotion.pojo.cache.SeckillSessionCacheDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 秒杀信息
 *
 * @author liuyongtao
 * @since 2021-8-30 16:43
 */
@Data
public class SeckillInfoVo {

    @ApiModelProperty(value = "限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态信息")
    private SeckillCacheDto seckillCacheDto;

    @ApiModelProperty(value = "限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品信息")
    private SeckillProductCacheDto seckillProductCacheDto;

    @ApiModelProperty(value = "限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段信息.")
    private SeckillSessionCacheDto seckillSessionCacheDto;
}
