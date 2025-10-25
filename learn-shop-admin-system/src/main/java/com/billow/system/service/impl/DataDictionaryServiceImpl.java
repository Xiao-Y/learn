package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.DataDictionaryDao;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.pojo.search.DataDictionarySearchParam;
import com.billow.system.pojo.vo.DataDictionaryVo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.utlis.ConvertUtils;
import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.billow.system.pojo.po.table.DataDictionaryPoTableDef.DATA_DICTIONARY_PO;

/**
 * 数据字典
 *
 * @author liuyongtao
 * @create 2019-07-11 10:58
 */
@Service
public class DataDictionaryServiceImpl extends HighLevelServiceImpl<DataDictionaryDao, DataDictionaryPo, DataDictionarySearchParam> implements DataDictionaryService {

    @Autowired
    private DataDictionaryDao dataDictionaryDao;

    @Override
    public Page<DataDictionaryPo> listByPage(DataDictionarySearchParam searchParam) {
        searchParam.setOrderBy(DATA_DICTIONARY_PO.ID.getName());
        Page<DataDictionaryPo> pages = this.findListByPage(searchParam);
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
    public List<DataDictionaryVo> findDataDictionaryByCondition(DataDictionarySearchParam searchParam) {
        searchParam.setOrderBy(DATA_DICTIONARY_PO.FIELD_ORDER.getName());
        List<DataDictionaryPo> dataDictionaryPos = this.findList(searchParam);
        return ConvertUtils.convert(dataDictionaryPos, DataDictionaryVo.class);
    }
}
