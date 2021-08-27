
package com.billow.seckill.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.seckill.pojo.po.SeckillSessionPo;
import com.billow.seckill.pojo.search.SeckillSessionSearchParam;

/**
 * <p>
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-27
 */
public interface SeckillSessionService extends IService<SeckillSessionPo> {

    /**
     * 分页查询
     *
     * @param seckillSessionSearchParam 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.seckill.pojo.po.SeckillSessionPo>
     * @author billow
     * @since 2021-08-27
     */
    IPage<SeckillSessionPo> findListByPage(SeckillSessionSearchParam seckillSessionSearchParam);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2021-08-27
     */
    boolean prohibitById(String id);
}