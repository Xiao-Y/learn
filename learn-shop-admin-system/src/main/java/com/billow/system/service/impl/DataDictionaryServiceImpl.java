package com.billow.system.service.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.jpa.DefaultSpec;
import com.billow.system.dao.DataDictionaryDao;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.pojo.vo.DataDictionaryVo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典
 *
 * @author liuyongtao
 * @create 2019-07-11 10:58
 */
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Autowired
    private DataDictionaryDao dataDictionaryDao;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<DataDictionaryVo> findDataDictionaryByCondition(DataDictionaryVo dataDictionaryVo) {
        DataDictionaryPo convert = ConvertUtils.convert(dataDictionaryVo, DataDictionaryPo.class);
        DefaultSpec<DataDictionaryPo> defaultSpec = new DefaultSpec<>(convert);
        Sort sort = new Sort(Sort.Direction.ASC, "fieldOrder");
        List<DataDictionaryPo> dataDictionaryPos = dataDictionaryDao.findAll(defaultSpec, sort);
        List<DataDictionaryVo> dataDictionaryVos = ConvertUtils.convert(dataDictionaryPos, DataDictionaryVo.class);
        return dataDictionaryVos;
    }
}
