
package com.billow.seckill.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.seckill.pojo.po.SeckillProductRelationPo;
import com.billow.seckill.pojo.search.SeckillProductRelationSearchParam;

/**
 * <p>
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-27
 */
public interface SeckillProductRelationService extends IService<SeckillProductRelationPo> {

    /**
     * 分页查询
     *
     * @param seckillProductRelationSearchParam 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.seckill.pojo.po.SeckillProductRelationPo>
     * @author billow
     * @since 2021-08-27
     */
    IPage<SeckillProductRelationPo> findListByPage(SeckillProductRelationSearchParam seckillProductRelationSearchParam);

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