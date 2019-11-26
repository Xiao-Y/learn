
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.ProductPo;
import com.billow.product.pojo.vo.ProductSkuVo;
import com.billow.product.pojo.vo.ProductVo;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
public interface ProductService extends IService<ProductPo> {

    /**
     * 分页查询
     *
     * @param productVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.ProductPo>
     * @author billow
     * @since 2019-11-26
     */
    IPage<ProductPo> findListByPage(ProductVo productVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2019-11-26
     */
    boolean prohibitById(String id);

    /**
     * 通过 productId 获取商品 sku 信息
     *
     * @param id
     * @return com.billow.product.pojo.vo.ProductSkuVo
     * @author LiuYongTao
     * @date 2019/11/26 11:40
     */
    ProductSkuVo findProductSku(String id);
}