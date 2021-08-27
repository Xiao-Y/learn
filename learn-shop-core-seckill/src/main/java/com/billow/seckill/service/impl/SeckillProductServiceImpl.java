package com.billow.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.seckill.dao.SeckillProductDao;
import com.billow.seckill.pojo.po.SeckillProductPo;
import com.billow.seckill.pojo.search.SeckillProductRelationSearchParam;
import com.billow.seckill.service.SeckillProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-27
 */
@Service
public class SeckillProductServiceImpl extends ServiceImpl<SeckillProductDao, SeckillProductPo> implements SeckillProductService {

    @Autowired
    private SeckillProductDao seckillProductRelationDao;

    @Override
    public IPage<SeckillProductPo> findListByPage(SeckillProductRelationSearchParam seckillProductRelationSearchParam) {
        IPage<SeckillProductPo> page = new Page<>(seckillProductRelationSearchParam.getPageNo(), seckillProductRelationSearchParam.getPageSize());
        LambdaQueryWrapper<SeckillProductPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<SeckillProductPo> selectPage = seckillProductRelationDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = seckillProductRelationDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        SeckillProductPo po = new SeckillProductPo();
        po.setValidInd(false);
        LambdaQueryWrapper<SeckillProductPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SeckillProductPo::getId, id);
        return seckillProductRelationDao.update(po, wrapper) >= 1;
    }
}

