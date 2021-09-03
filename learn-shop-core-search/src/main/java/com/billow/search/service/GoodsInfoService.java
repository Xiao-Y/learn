package com.billow.search.service;

import com.billow.search.pojo.po.GoodsInfoPo;
import org.springframework.data.domain.Page;

/**
 * @author liuyongtao
 * @since 2021-9-2 20:04
 */
public interface GoodsInfoService {

    /**
     * 通过id 查询商品信息
     *
     * @param id
     * @return {@link GoodsInfoPo}
     * @author liuyongtao
     * @since 2021-9-2 20:06
     */
    GoodsInfoPo getById(Long id);

    /**
     * 更新或保存商品信息
     *
     * @param goodsInfoPo
     * @author liuyongtao
     * @since 2021-9-2 20:57
     */
    void saveOrUpdate(GoodsInfoPo goodsInfoPo);

    /**
     * 分布查询出ID
     *
     * @param goodsInfoPo
     * @return {@link Page< GoodsInfoPo>}
     * @author liuyongtao
     * @since 2021-9-2 21:27
     */
    Page<GoodsInfoPo> findIdByPage(GoodsInfoPo goodsInfoPo);

    /**
     * 根据主键删除商品信息
     *
     * @param id
     * @author liuyongtao
     * @since 2021-9-3 8:30
     */
    void delById(Long id);
}
