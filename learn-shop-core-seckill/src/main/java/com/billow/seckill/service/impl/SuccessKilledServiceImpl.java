package com.billow.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.seckill.dao.SuccessKilledDao;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import com.billow.seckill.pojo.search.SuccessKilledSearchParam;
import com.billow.seckill.service.SuccessKilledService;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
public class SuccessKilledServiceImpl extends HighLevelServiceImpl<SuccessKilledDao, SuccessKilledPo, SuccessKilledSearchParam> implements SuccessKilledService {

    @Override
    public void genQueryCondition(LambdaQueryWrapper<SuccessKilledPo> wrapper, SuccessKilledSearchParam successKilledVo) {
        wrapper.eq(Objects.nonNull(successKilledVo.getValidInd()), SuccessKilledPo::getValidInd, successKilledVo.getValidInd());
    }
}

