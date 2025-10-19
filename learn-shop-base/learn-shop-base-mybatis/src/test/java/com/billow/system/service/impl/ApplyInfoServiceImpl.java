package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.ApplyInfoDao;
import com.billow.system.pojo.search.ApplyInfoSearchParam;
import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.service.ApplyInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2025-10-19
 */
@Service
public class ApplyInfoServiceImpl extends HighLevelServiceImpl<ApplyInfoDao, ApplyInfoPo,ApplyInfoSearchParam> implements ApplyInfoService {

}

