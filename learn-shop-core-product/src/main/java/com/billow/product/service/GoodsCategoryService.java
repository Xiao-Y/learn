
package com.billow.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.GoodsCategoryPo;
import com.billow.product.pojo.search.GoodsCategorySearchParam;
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
public interface GoodsCategoryService extends HighLevelService<GoodsCategoryPo, GoodsCategorySearchParam> {

    /**
     * 查询分类表数据
     *
     * @param goodsCategoryVo
     * @return java.util.List<com.billow.product.pojo.po.GoodsCategoryPo>
     * @author LiuYongTao
     * @date 2019/12/5 10:54
     */
    List<GoodsCategoryPo> findList(GoodsCategoryVo goodsCategoryVo);

    /**
     * 通过父ID查询分类树
     *
     * @param parentId
     * @return {@link List< GoodsCategoryPo>}
     * @author liuyongtao
     * @since 2021-9-9 8:55
     */
    List<GoodsCategoryPo> findCategoryTree(Long parentId);
}