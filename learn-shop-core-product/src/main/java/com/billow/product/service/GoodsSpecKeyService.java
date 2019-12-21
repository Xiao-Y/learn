
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.vo.GoodsSpecKeyVo;

import java.util.List;

/**
 * <p>
 * 规格表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsSpecKeyService extends IService<GoodsSpecKeyPo> {

    /**
     * 分页查询
     *
     * @param goodsSpecKeyVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.product.pojo.po.GoodsSpecKeyPo>
     * @author billow
     * @since 2019-11-27
     */
    IPage<GoodsSpecKeyPo> findListByPage(GoodsSpecKeyVo goodsSpecKeyVo);

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
     * 通过 CategoryId 查询出所有的规格 KEY
     *
     * @param categoryId
     * @return java.util.List<com.billow.product.pojo.po.GoodsSpecKeyPo>
     * @author LiuYongTao
     * @date 2019/11/29 10:25
     */
    List<GoodsSpecKeyPo> findListByCategoryId(String categoryId);

    /**
     * 保存一组规格信息
     *
     * @param goodsSpecKeyVos
     * @return java.util.List<com.billow.product.pojo.vo.GoodsSpecKeyVo>
     * @author LiuYongTao
     * @date 2019/12/4 11:05
     */
    List<GoodsSpecKeyVo> saveList(List<GoodsSpecKeyVo> goodsSpecKeyVos);
}