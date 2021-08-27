package com.billow.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.seckill.dao.SeckillProductRelationDao;
import com.billow.seckill.pojo.po.SeckillProductRelationPo;
import com.billow.seckill.pojo.search.SeckillProductRelationSearchParam;
import com.billow.seckill.service.SeckillProductRelationService;
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
public class SeckillProductRelationServiceImpl extends ServiceImpl<SeckillProductRelationDao, SeckillProductRelationPo> implements SeckillProductRelationService {

    @Autowired
    private SeckillProductRelationDao seckillProductRelationDao;

    @Override
    public IPage<SeckillProductRelationPo> findListByPage(SeckillProductRelationSearchParam seckillProductRelationSearchParam) {
        IPage<SeckillProductRelationPo> page = new Page<>(seckillProductRelationSearchParam.getPageNo(), seckillProductRelationSearchParam.getPageSize());
        LambdaQueryWrapper<SeckillProductRelationPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<SeckillProductRelationPo> selectPage = seckillProductRelationDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = seckillProductRelationDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        SeckillProductRelationPo po = new SeckillProductRelationPo();
        po.setValidInd(false);
        LambdaQueryWrapper<SeckillProductRelationPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SeckillProductRelationPo::getId, id);
        return seckillProductRelationDao.update(po, wrapper) >= 1;
    }
}

