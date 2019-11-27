package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.ShopInfoDao;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.pojo.vo.ShopInfoVo;
import com.billow.product.service.ShopInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class ShopInfoServiceImpl extends ServiceImpl<ShopInfoDao, ShopInfoPo> implements ShopInfoService {

    @Autowired
    private ShopInfoDao shopInfoDao;

    @Override
    public IPage<ShopInfoPo> findListByPage(ShopInfoVo shopInfoVo) {
        IPage<ShopInfoPo> page = new Page<>(shopInfoVo.getPageNo(), shopInfoVo.getPageSize());
        LambdaQueryWrapper<ShopInfoPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<ShopInfoPo> selectPage = shopInfoDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        ShopInfoPo po = new ShopInfoPo();
        po.setValidInd(false);
        LambdaQueryWrapper<ShopInfoPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShopInfoPo::getId, id);
        return shopInfoDao.update(po, wrapper) >= 1;
    }
}

