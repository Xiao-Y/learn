package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsSkuSpecValueDao;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.search.GoodsSkuSpecValueSearchParam;
import com.billow.product.pojo.vo.GoodsSkuSpecValueVo;
import com.billow.product.service.GoodsSkuSpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * sku规格值 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsSkuSpecValueServiceImpl extends HighLevelServiceImpl<GoodsSkuSpecValueDao, GoodsSkuSpecValuePo, GoodsSkuSpecValueSearchParam> implements GoodsSkuSpecValueService {

    @Autowired
    private GoodsSkuSpecValueDao goodsSkuSpecValueDao;

    @Override
    public List<Long> findSpuSpecKey(Long id) {
        LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSkuSpecValuePo::getSpecKeyId, id);
        List<GoodsSkuSpecValuePo> skuSpecValuePos = goodsSkuSpecValueDao.selectList(wrapper);
        return skuSpecValuePos.stream().map(m -> m.getSpecKeyId()).collect(Collectors.toList());
    }
}

