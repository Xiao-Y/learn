
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.search.GoodsSpuSpecSearchParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * spu规格表 服务类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
public interface GoodsSpuSpecService extends HighLevelService<GoodsSpuSpecPo, GoodsSpuSpecSearchParam> {


    /**
     * 根据 spuId 查询spu规格表数据
     *
     * @param spuId
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author LiuYongTao
     * @date 2019/11/27 11:33
     */
    List<Map<String, Object>> findSpuSpec(Long spuId);

    /**
     * 根据 spuId 查询 spu 规格Key数据
     *
     * @param spuId
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/5 16:33
     */
    List<Long> findSpuSpecKey(Long spuId);

}