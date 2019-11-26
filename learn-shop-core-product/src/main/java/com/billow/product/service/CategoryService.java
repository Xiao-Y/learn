
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.CategoryPo;
import com.billow.product.pojo.vo.CategoryVo;

/**
 * <p>
 * 商品分类 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
public interface CategoryService extends IService<CategoryPo> {

    /**
     * 分页查询
     *
     * @param productVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.CategoryPo>
     * @author billow
     * @since 2019-11-26
     */
    IPage<CategoryPo> findListByPage(CategoryVo categoryVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2019-11-26
     */
    boolean prohibitById(String id);
}