
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.vo.GoodsSpecValueVo;

/**
 * <p>
 * 规格值表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsSpecValueService extends IService<GoodsSpecValuePo> {

    /**
     * 分页查询
     *
     * @param goodsSpecValueVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsSpecValuePo>
     * @author billow
     * @since 2019-11-27
     */
    IPage<GoodsSpecValuePo> findListByPage(GoodsSpecValueVo goodsSpecValueVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2019-11-27
     */
    boolean prohibitById(String id);
}