
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.ProductSpcesPo;
import com.billow.product.pojo.vo.ProductSpcesVo;

/**
 * <p>
 * 商品规格 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
public interface ProductSpcesService extends IService<ProductSpcesPo> {

    /**
     * 分页查询
     *
     * @param productVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.ProductSpcesPo>
     * @author billow
     * @since 2019-11-26
     */
    IPage<ProductSpcesPo> findListByPage(ProductSpcesVo productSpcesVo);

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