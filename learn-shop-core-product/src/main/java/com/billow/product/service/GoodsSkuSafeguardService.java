
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsSkuSafeguardPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.vo.GoodsSkuSafeguardVo;

/**
 * <p>
 * sku增值保障 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
public interface GoodsSkuSafeguardService extends IService<GoodsSkuSafeguardPo> {

    /**
     * 分页查询
     *
     * @param goodsSkuSafeguardVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsSkuSafeguardPo>
     * @author billow
     * @since 2021-02-06
     */
    IPage<GoodsSkuSafeguardPo> findListByPage(GoodsSkuSafeguardVo goodsSkuSafeguardVo);

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