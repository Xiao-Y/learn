package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.CityDao;
import com.billow.system.pojo.search.CitySearchParam;
import com.billow.system.pojo.po.CityPo;
import com.billow.system.service.CityService;
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
public class CityServiceImpl extends HighLevelServiceImpl<CityDao, CityPo,CitySearchParam> implements CityService {

}

