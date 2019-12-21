
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.vo.GoodsSpuVo;

/**
 * <p>
 * spu表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsSpuService extends IService<GoodsSpuPo> {

    /**
     * 分页查询
     *
     * @param goodsSpuVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsSpuPo>
     * @author billow
     * @since 2019-11-27
     */
    IPage<GoodsSpuPo> findListByPage(GoodsSpuVo goodsSpuVo);

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
     * 添加或者更新
     *
     * @param goodsSpuVo
     * @return void
     * @author LiuYongTao
     * @date 2019/12/5 17:04
     */
    void addOrUpdate(GoodsSpuVo goodsSpuVo);
}