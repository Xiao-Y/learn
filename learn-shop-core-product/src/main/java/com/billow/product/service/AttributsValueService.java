
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.AttributsValuePo;
import com.billow.product.pojo.vo.AttributsValueVo;

/**
 * <p>
 * 商品属性值 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
public interface AttributsValueService extends IService<AttributsValuePo> {

    /**
     * 分页查询
     *
     * @param productVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.AttributsValuePo>
     * @author billow
     * @since 2019-11-26
     */
    IPage<AttributsValuePo> findListByPage(AttributsValueVo attributsValueVo);

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