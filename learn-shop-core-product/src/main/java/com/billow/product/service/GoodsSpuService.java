
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.search.GoodsSpuSearchParam;
import com.billow.product.pojo.vo.GoodsSpuVo;

/**
 * <p>
 * spu表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsSpuService extends HighLevelService<GoodsSpuPo, GoodsSpuSearchParam> {

    /**
     * 添加或者更新
     *
     * @param goodsSpuVo
     * @return void
     * @author LiuYongTao
     * @date 2019/12/5 17:04
     */
    void addOrUpdate(GoodsSpuVo goodsSpuVo);
}