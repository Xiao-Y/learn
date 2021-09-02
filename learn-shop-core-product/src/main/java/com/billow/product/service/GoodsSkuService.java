
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.GoodsSkuPo;
import com.billow.product.pojo.search.GoodsSkuSearchParam;
import com.billow.product.pojo.vo.GoodsSkuVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsSkuService extends HighLevelService<GoodsSkuPo, GoodsSkuSearchParam> {

    /**
     * 通过 spuId 获取商品 sku 规格信息
     *
     * @param spuId
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author LiuYongTao
     * @date 2019/11/27 14:18
     */
    List<Map<String, Object>> findGoodsSkuSpec(Long spuId);

    /**
     * 通过 spuId 获取商品 sku 信息
     *
     * @param spuId
     * @return java.util.List<com.billow.product.pojo.vo.GoodsSkuVo>
     * @author LiuYongTao
     * @date 2019/11/28 14:04
     */
    List<GoodsSkuVo> findGoodsSku(Long spuId);

    /**
     * 新增sku表数据
     *
     * @param vo
     * @return void
     * @author LiuYongTao
     * @date 2019/12/2 15:00
     */
    void add(GoodsSkuVo vo);

    /**
     * 更新 sku
     *
     * @param goodsSkuVo
     * @return void
     * @author LiuYongTao
     * @date 2019/12/2 15:17
     */
    void update(GoodsSkuVo goodsSkuVo);

    /**
     * 根据 spuId 查询 sku 规格表数据
     *
     * @param spuId
     * @return {@link List< Map< String, Object>>}
     * @author xiaoy
     * @since 2021/2/6 15:09
     */
    List<Map<String, Object>> findSpuSpec(Long spuId);
}