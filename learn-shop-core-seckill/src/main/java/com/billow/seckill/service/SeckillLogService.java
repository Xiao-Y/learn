
package com.billow.seckill.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.seckill.pojo.po.SeckillLogPo;
import com.billow.seckill.pojo.search.SeckillLogSearchParam;

/**
 * <p>
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-27
 */
public interface SeckillLogService extends IService<SeckillLogPo> {

    /**
     * 分页查询
     *
     * @param seckillLogSearchParam 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.seckill.pojo.po.SeckillLogPo>
     * @author billow
     * @since 2021-08-27
     */
    IPage<SeckillLogPo> findListByPage(SeckillLogSearchParam seckillLogSearchParam);

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