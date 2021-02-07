
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.vo.GoodsSkuSpecValueVo;

import java.util.List;

/**
 * <p>
 * sku规格值 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsSkuSpecValueService extends IService<GoodsSkuSpecValuePo> {

    /**
     * 分页查询
     *
     * @param goodsSkuSpecValueVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsSkuSpecValuePo>
     * @author billow
     * @since 2019-11-27
     */
    IPage<GoodsSkuSpecValuePo> findListByPage(GoodsSkuSpecValueVo goodsSkuSpecValueVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2019-11-27
     */
    boolean prohibitById(Long id);

    /**
     * 根据 spuId 查询出 skuId
     *
     * @param id
     * @return {@link List< Long>}
     * @author xiaoy
     * @since 2021/2/6 15:34
     */
    List<Long> findSpuSpecKey(Long id);
}