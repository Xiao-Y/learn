package com.billow.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.seckill.dao.SeckillSessionDao;
import com.billow.seckill.pojo.po.SeckillSessionPo;
import com.billow.seckill.pojo.search.SeckillSessionSearchParam;
import com.billow.seckill.service.SeckillSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-27
 */
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionPo> implements SeckillSessionService {

    @Autowired
    private SeckillSessionDao seckillSessionDao;

    @Override
    public IPage<SeckillSessionPo> findListByPage(SeckillSessionSearchParam seckillSessionSearchParam) {
        IPage<SeckillSessionPo> page = new Page<>(seckillSessionSearchParam.getPageNo(), seckillSessionSearchParam.getPageSize());
        LambdaQueryWrapper<SeckillSessionPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<SeckillSessionPo> selectPage = seckillSessionDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = seckillSessionDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        SeckillSessionPo po = new SeckillSessionPo();
        po.setValidInd(false);
        LambdaQueryWrapper<SeckillSessionPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SeckillSessionPo::getId, id);
        return seckillSessionDao.update(po, wrapper) >= 1;
    }
}

