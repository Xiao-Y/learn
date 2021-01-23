
package com.billow.seckill.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.seckill.pojo.vo.SuccessKilledVo;

/**
 * <p>
 * 秒杀成功明细表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
public interface SuccessKilledService extends IService<SuccessKilledPo> {

    /**
     * 分页查询
     *
     * @param successKilledVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.seckill.pojo.po.SuccessKilledPo>
     * @author billow
     * @since 2021-01-21
     */
    IPage<SuccessKilledPo> findListByPage(SuccessKilledVo successKilledVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2021-01-21
     */
    boolean prohibitById(Long id);
}