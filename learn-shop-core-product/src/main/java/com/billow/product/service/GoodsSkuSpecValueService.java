
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.search.GoodsSkuSpecValueSearchParam;

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
public interface GoodsSkuSpecValueService extends HighLevelService<GoodsSkuSpecValuePo, GoodsSkuSpecValueSearchParam> {

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