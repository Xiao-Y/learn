package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.SuccessKilledDao;
import com.billow.order.pojo.po.SuccessKilledPo;
import com.billow.order.pojo.search.SuccessKilledSearchParam;
import com.billow.order.service.SuccessKilledService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 秒杀成功明细表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-21
 */
@Service
public class SuccessKilledServiceImpl extends HighLevelServiceImpl<SuccessKilledDao, SuccessKilledPo, SuccessKilledSearchParam> implements SuccessKilledService {

    @Async
    @Transactional
    @Override
    public void saveAsync(SuccessKilledPo entity) {
        super.save(entity);
    }
}

