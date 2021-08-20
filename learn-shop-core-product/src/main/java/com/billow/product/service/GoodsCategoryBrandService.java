
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsCategoryBrandPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.vo.GoodsCategoryBrandVo;

/**
 * <p>
 * 商品分类和品牌的中间表，两者是多对多关系 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
public interface GoodsCategoryBrandService extends IService<GoodsCategoryBrandPo> {

    /**
     * 分页查询
     *
     * @param goodsCategoryBrandVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsCategoryBrandPo>
     * @author billow
     * @since 2021-02-06
     */
    IPage<GoodsCategoryBrandPo> findListByPage(GoodsCategoryBrandVo goodsCategoryBrandVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2021-02-06
     */
    boolean prohibitById(String id);
}