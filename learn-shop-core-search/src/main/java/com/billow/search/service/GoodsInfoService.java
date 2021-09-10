package com.billow.search.service;

import com.billow.aop.commons.CustomPage;
import com.billow.search.pojo.po.GoodsInfoPo;
import com.billow.search.pojo.search.GoodsInfoSearchParam;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.io.IOException;
import java.util.Map;

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
     * 根据主键删除商品信息
     *
     * @param id
     * @author liuyongtao
     * @since 2021-9-3 8:30
     */
    void delById(Long id);

    /**
     * 条件更新
     *
     * @param condition 更新条件
     * @param updateVle 更新值
     * @author liuyongtao
     * @since 2021-9-6 9:29
     */
    void updateByCondition(Map<String, Object> condition, Map<String, Object> updateVle) throws IOException;

    /**
     * 条件查询
     *
     * @param param    查询参数
     * @param pageSize 页面大小
     * @param pageNo   当前页
     * @return {@link SearchHits<GoodsInfoPo>}
     * @author liuyongtao
     * @since 2021-9-6 11:37
     */
    CustomPage search(Integer pageNo, Integer pageSize, GoodsInfoSearchParam param);
}
