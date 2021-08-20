package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSafeguardDao;
import com.billow.product.pojo.po.GoodsSafeguardPo;
import com.billow.product.pojo.vo.GoodsSafeguardVo;
import com.billow.product.service.GoodsSafeguardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 增值保障 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
@Service
public class GoodsSafeguardServiceImpl extends ServiceImpl<GoodsSafeguardDao, GoodsSafeguardPo> implements GoodsSafeguardService {

    @Autowired
    private GoodsSafeguardDao goodsSafeguardDao;

    @Override
    public IPage<GoodsSafeguardPo> findListByPage(GoodsSafeguardVo goodsSafeguardVo) {
        IPage<GoodsSafeguardPo> page = new Page<>(goodsSafeguardVo.getPageNo(), goodsSafeguardVo.getPageSize());
        LambdaQueryWrapper<GoodsSafeguardPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsSafeguardPo> selectPage = goodsSafeguardDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = goodsSafeguardDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSafeguardPo po = new GoodsSafeguardPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSafeguardPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSafeguardPo::getId, id);
        return goodsSafeguardDao.update(po, wrapper) >= 1;
    }
}

