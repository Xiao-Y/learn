package com.billow.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.seckill.dao.SuccessKilledDao;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import com.billow.seckill.pojo.vo.SuccessKilledVo;
import com.billow.seckill.service.SuccessKilledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀成功明细表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
@Service
public class SuccessKilledServiceImpl extends ServiceImpl<SuccessKilledDao, SuccessKilledPo> implements SuccessKilledService {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Override
    public IPage<SuccessKilledPo> findListByPage(SuccessKilledVo successKilledVo) {
        IPage<SuccessKilledPo> page = new Page<>(successKilledVo.getPageNo(), successKilledVo.getPageSize());
        LambdaQueryWrapper<SuccessKilledPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<SuccessKilledPo> selectPage = successKilledDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        SuccessKilledPo po = new SuccessKilledPo();
        po.setValidInd(false);
        LambdaQueryWrapper<SuccessKilledPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SuccessKilledPo::getId, id);
        return successKilledDao.update(po, wrapper) >= 1;
    }
}

