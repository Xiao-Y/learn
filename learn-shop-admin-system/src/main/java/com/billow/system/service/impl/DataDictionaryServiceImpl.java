package com.billow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.mybatis.utils.MybatisKet;
import com.billow.system.dao.DataDictionaryDao;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.pojo.vo.DataDictionaryVo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典
 *
 * @author liuyongtao
 * @create 2019-07-11 10:58
 */
@Service
public class DataDictionaryServiceImpl extends ServiceImpl<DataDictionaryDao, DataDictionaryPo> implements DataDictionaryService {

    @Autowired
    private DataDictionaryDao dataDictionaryDao;

    @Override
    public IPage<DataDictionaryPo> listByPage(DataDictionaryVo dataDictionaryVo) {
        DataDictionaryPo convert = ConvertUtils.convert(dataDictionaryVo, DataDictionaryPo.class);
        QueryWrapper<DataDictionaryPo> condition = MybatisKet.getCondition(convert);
        condition.orderByDesc("id");
//        condition.orderByAsc("field_type", "field_order");
        IPage<DataDictionaryPo> page = new Page<>(dataDictionaryVo.getPageNo(), dataDictionaryVo.getPageSize());
        IPage<DataDictionaryPo> pages = this.page(page, condition);
        return pages;
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
        DataDictionaryPo dictionaryPo = ConvertUtils.convert(dataDictionaryVo, DataDictionaryPo.class);
        this.saveOrUpdate(dictionaryPo);
        ConvertUtils.convert(dictionaryPo, dataDictionaryVo);
    }

    @Override
    public DataDictionaryVo prohibitById(Long id) {
        DataDictionaryPo po = this.getById(id);
        po.setValidInd(false);
        LambdaQueryWrapper<DataDictionaryPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataDictionaryPo::getId, id);
        dataDictionaryDao.update(po, wrapper);
        return ConvertUtils.convert(po, DataDictionaryVo.class);
    }

    @Override
    public List<DataDictionaryVo> findDataDictionaryByCondition(DataDictionaryVo dataDictionaryVo) {
        DataDictionaryPo convert = ConvertUtils.convert(dataDictionaryVo, DataDictionaryPo.class);
        QueryWrapper<DataDictionaryPo> condition = MybatisKet.getCondition(convert);
        condition.orderByAsc("field_order");
        List<DataDictionaryPo> dataDictionaryPos = dataDictionaryDao.selectList(condition);
        return ConvertUtils.convert(dataDictionaryPos, DataDictionaryVo.class);
    }
}
