
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsSafeguardPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.vo.GoodsSafeguardVo;

/**
 * <p>
 * 增值保障 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
public interface GoodsSafeguardService extends IService<GoodsSafeguardPo> {

    /**
     * 分页查询
     *
     * @param goodsSafeguardVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsSafeguardPo>
     * @author billow
     * @since 2021-02-06
     */
    IPage<GoodsSafeguardPo> findListByPage(GoodsSafeguardVo goodsSafeguardVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2021-02-06
     */
    boolean prohibitById(String id);
}