package com.billow.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.seckill.dao.SeckillLogDao;
import com.billow.seckill.pojo.po.SeckillLogPo;
import com.billow.seckill.pojo.search.SeckillLogSearchParam;
import com.billow.seckill.service.SeckillLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-27
 */
@Service
public class SeckillLogServiceImpl extends ServiceImpl<SeckillLogDao, SeckillLogPo> implements SeckillLogService {

    @Autowired
    private SeckillLogDao seckillLogDao;

    @Override
    public IPage<SeckillLogPo> findListByPage(SeckillLogSearchParam seckillLogSearchParam) {
        IPage<SeckillLogPo> page = new Page<>(seckillLogSearchParam.getPageNo(), seckillLogSearchParam.getPageSize());
        LambdaQueryWrapper<SeckillLogPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<SeckillLogPo> selectPage = seckillLogDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = seckillLogDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        SeckillLogPo po = new SeckillLogPo();
        po.setValidInd(false);
        LambdaQueryWrapper<SeckillLogPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SeckillLogPo::getId, id);
        return seckillLogDao.update(po, wrapper) >= 1;
    }
}

