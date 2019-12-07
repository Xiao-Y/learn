
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.GoodsSkuPo;
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
public interface GoodsSkuService extends IService<GoodsSkuPo> {

    /**
     * 分页查询
     *
     * @param goodsSkuVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsSkuPo>
     * @author billow
     * @since 2019-11-27
     */
    IPage<GoodsSkuPo> findListByPage(GoodsSkuVo goodsSkuVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2019-11-27
     */
    boolean prohibitById(String id);

    /**
     * 通过 spuId 获取商品 sku 规格信息
     *
     * @param spuId
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author LiuYongTao
     * @date 2019/11/27 14:18
     */
    List<Map<String, Object>> findGoodsSkuSpec(String spuId);

    /**
     * 通过 spuId 获取商品 sku 信息
     *
     * @param spuId
     * @return java.util.List<com.billow.product.pojo.vo.GoodsSkuVo>
     * @author LiuYongTao
     * @date 2019/11/28 14:04
     */
    List<GoodsSkuVo> findGoodsSku(String spuId);

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
}