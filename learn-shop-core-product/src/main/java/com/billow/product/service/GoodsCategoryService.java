
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.GoodsCategoryPo;
import com.billow.product.pojo.vo.GoodsCategoryVo;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsCategoryService extends IService<GoodsCategoryPo> {

    /**
     * 分页查询
     *
     * @param goodsCategoryVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsCategoryPo>
     * @author billow
     * @since 2019-11-27
     */
    IPage<GoodsCategoryPo> findListByPage(GoodsCategoryVo goodsCategoryVo);

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
     * 查询分类表数据
     *
     * @param goodsCategoryVo
     * @return java.util.List<com.billow.product.pojo.po.GoodsCategoryPo>
     * @author LiuYongTao
     * @date 2019/12/5 10:54
     */
    List<GoodsCategoryPo> findList(GoodsCategoryVo goodsCategoryVo);
}