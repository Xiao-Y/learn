package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.AttributeKeyDao;
import com.billow.product.pojo.po.AttributeKeyPo;
import com.billow.product.pojo.vo.AttributeKeyVo;
import com.billow.product.service.AttributeKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品属性key 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
@Service
public class AttributeKeyServiceImpl extends ServiceImpl<ProductDao, AttributeKeyPo> implements AttributeKeyService {

    @Autowired
    private AttributeKeyDao attributeKeyDao;

    @Override
    public IPage<AttributeKeyPo> findListByPage(AttributeKeyVo attributeKeyVo) {
        IPage<AttributeKeyPo> page = new Page<>(attributeKeyVo.getPageNo(), attributeKeyVo.getPageSize());
        LambdaQueryWrapper<AttributeKeyPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<AttributeKeyPo> selectPage = attributeKeyDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        AttributeKeyPo po = new AttributeKeyPo();
        po.setValidInd(false);
        LambdaQueryWrapper<AttributeKeyPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AttributeKeyPo::getId, id);
        return attributeKeyDao.update(po, wrapper) >= 1;
    }
}

