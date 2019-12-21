package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSpecValueDao;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
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
public class GoodsSpecValueServiceImpl extends ServiceImpl<GoodsSpecValueDao, GoodsSpecValuePo> implements GoodsSpecValueService {

    @Autowired
    private GoodsSpecValueDao goodsSpecValueDao;

    @Override
    public IPage<GoodsSpecValuePo> findListByPage(GoodsSpecValueVo goodsSpecValueVo) {
        IPage<GoodsSpecValuePo> page = new Page<>(goodsSpecValueVo.getPageNo(), goodsSpecValueVo.getPageSize());
        LambdaQueryWrapper<GoodsSpecValuePo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsSpecValuePo> selectPage = goodsSpecValueDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSpecValuePo po = new GoodsSpecValuePo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSpecValuePo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpecValuePo::getId, id);
        return goodsSpecValueDao.update(po, wrapper) >= 1;
    }

    @Override
    public List<GoodsSpecValueVo> findListBySpecKeyId(String specKeyId) {
        LambdaQueryWrapper<GoodsSpecValuePo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpecValuePo::getSpecKeyId, specKeyId);
        List<GoodsSpecValuePo> goodsSpecValuePos = goodsSpecValueDao.selectList(wrapper);
        return ConvertUtils.convert(goodsSpecValuePos, GoodsSpecValueVo.class);
    }
}

