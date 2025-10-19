package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.DataDictionaryDao;
import com.billow.system.pojo.search.DataDictionarySearchParam;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.service.DataDictionaryService;
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
public class DataDictionaryServiceImpl extends HighLevelServiceImpl<DataDictionaryDao, DataDictionaryPo,DataDictionarySearchParam> implements DataDictionaryService {

}

