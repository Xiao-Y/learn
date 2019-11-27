
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.pojo.vo.ShopInfoVo;

/**
 * <p>
 * 店铺表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface ShopInfoService extends IService<ShopInfoPo> {

    /**
     * 分页查询
     *
     * @param shopInfoVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.ShopInfoPo>
     * @author billow
     * @since 2019-11-27
     */
    IPage<ShopInfoPo> findListByPage(ShopInfoVo shopInfoVo);

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