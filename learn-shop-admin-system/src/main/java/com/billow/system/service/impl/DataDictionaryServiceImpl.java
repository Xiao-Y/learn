package com.billow.system.service.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.jpa.DefaultSpec;
import com.billow.system.dao.DataDictionaryDao;
import com.billow.system.init.IStartLoading;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.pojo.vo.DataDictionaryVo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Page<DataDictionaryPo> listByPage(DataDictionaryVo dataDictionaryVo) {
        DataDictionaryPo convert = ConvertUtils.convert(dataDictionaryVo, DataDictionaryPo.class);
        DefaultSpec<DataDictionaryPo> defaultSpec = new DefaultSpec<>(convert);
        Sort sort = new Sort(Sort.Direction.ASC, "fieldType", "fieldOrder");
        Pageable pageable = new PageRequest(dataDictionaryVo.getPageNo(), dataDictionaryVo.getPageSize(), sort);
        Page<DataDictionaryPo> page = dataDictionaryDao.findAll(defaultSpec, pageable);
        return page;
    }

    @Override
    public List<String> findFieldType() {
        return dataDictionaryDao.findFieldType();
    }

    @Override
    public List<String> findSysModule() {
        return dataDictionaryDao.findSysModule();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void delById(Long id) {
        dataDictionaryDao.deleteById(id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveOrUpdate(DataDictionaryVo dataDictionaryVo) {
        DataDictionaryPo convert = ConvertUtils.convert(dataDictionaryVo, DataDictionaryPo.class);
        DataDictionaryPo dictionaryPo = dataDictionaryDao.save(convert);
        ConvertUtils.convert(dictionaryPo, dataDictionaryVo);
    }

    @Override
    public DataDictionaryVo prohibitById(Long id) {
        Optional<DataDictionaryPo> optional = dataDictionaryDao.findById(id);
        DataDictionaryPo dataDictionaryPo = optional.get();
        dataDictionaryPo.setValidInd(false);
        dataDictionaryDao.save(dataDictionaryPo);
        return ConvertUtils.convert(dataDictionaryPo, DataDictionaryVo.class);
    }

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
