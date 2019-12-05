
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.vo.GoodsSpuSpecVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * spu规格表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsSpuSpecService extends IService<GoodsSpuSpecPo> {

    /**
     * 分页查询
     *
     * @param goodsSpuSpecVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsSpuSpecPo>
     * @author billow
     * @since 2019-11-27
     */
    IPage<GoodsSpuSpecPo> findListByPage(GoodsSpuSpecVo goodsSpuSpecVo);

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
     * 根据 spuId 查询spu规格表数据
     *
     * @param spuId
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author LiuYongTao
     * @date 2019/11/27 11:33
     */
    List<Map<String, Object>> findSpuSpec(String spuId);

    /**
     * 根据 spuId 查询 spu 规格Key数据
     *
     * @param spuId
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/12/5 16:33
     */
    List<String> findSpuSpecKey(String spuId);

}