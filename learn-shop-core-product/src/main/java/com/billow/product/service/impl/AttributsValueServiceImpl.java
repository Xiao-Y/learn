package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.AttributsValueDao;
import com.billow.product.pojo.po.AttributsValuePo;
import com.billow.product.pojo.vo.AttributsValueVo;
import com.billow.product.service.AttributsValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品属性值 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
@Service
public class AttributsValueServiceImpl extends ServiceImpl<ProductDao, AttributsValuePo> implements AttributsValueService {

    @Autowired
    private AttributsValueDao attributsValueDao;

    @Override
    public IPage<AttributsValuePo> findListByPage(AttributsValueVo attributsValueVo) {
        IPage<AttributsValuePo> page = new Page<>(attributsValueVo.getPageNo(), attributsValueVo.getPageSize());
        LambdaQueryWrapper<AttributsValuePo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<AttributsValuePo> selectPage = attributsValueDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        AttributsValuePo po = new AttributsValuePo();
        po.setValidInd(false);
        LambdaQueryWrapper<AttributsValuePo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AttributsValuePo::getId, id);
        return attributsValueDao.update(po, wrapper) >= 1;
    }
}

