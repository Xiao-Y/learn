
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.search.GoodsSpecKeySearchParam;
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
public interface GoodsSpecKeyService extends HighLevelService<GoodsSpecKeyPo, GoodsSpecKeySearchParam> {

    /**
     * 通过 CategoryId 查询出所有的规格 KEY
     *
     * @param categoryId
     * @return java.util.List<com.billow.product.pojo.po.GoodsSpecKeyPo>
     * @author LiuYongTao
     * @date 2019/11/29 10:25
     */
    List<GoodsSpecKeyPo> findListByCategoryId(Long categoryId);

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