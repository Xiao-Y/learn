package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.MytasklistDao;
import com.billow.system.pojo.search.MytasklistSearchParam;
import com.billow.system.pojo.po.MytasklistPo;
import com.billow.system.service.MytasklistService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2025-10-19
 */
@Service
public class MytasklistServiceImpl extends HighLevelServiceImpl<MytasklistDao, MytasklistPo,MytasklistSearchParam> implements MytasklistService {

}

