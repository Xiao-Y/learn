package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsSpecValueDao;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.search.GoodsSpecValueSearchParam;
import com.billow.product.pojo.vo.GoodsSpecValueVo;
import com.billow.product.service.GoodsSpecValueService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 规格值表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsSpecValueServiceImpl extends HighLevelServiceImpl<GoodsSpecValueDao, GoodsSpecValuePo, GoodsSpecValueSearchParam> implements GoodsSpecValueService {

    @Autowired
    private GoodsSpecValueDao goodsSpecValueDao;

    @Override
    public List<GoodsSpecValueVo> findListBySpecKeyId(Long specKeyId) {
        LambdaQueryWrapper<GoodsSpecValuePo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpecValuePo::getSpecKeyId, specKeyId);
        List<GoodsSpecValuePo> goodsSpecValuePos = goodsSpecValueDao.selectList(wrapper);
        return ConvertUtils.convert(goodsSpecValuePos, GoodsSpecValueVo.class);
    }
}

