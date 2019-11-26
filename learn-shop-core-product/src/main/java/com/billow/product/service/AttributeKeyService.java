
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.AttributeKeyPo;
import com.billow.product.pojo.vo.AttributeKeyVo;

/**
 * <p>
 * 商品属性key 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
public interface AttributeKeyService extends IService<AttributeKeyPo> {

    /**
     * 分页查询
     *
     * @param productVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.AttributeKeyPo>
     * @author billow
     * @since 2019-11-26
     */
    IPage<AttributeKeyPo> findListByPage(AttributeKeyVo attributeKeyVo);

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