package com.billow.system.init.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.DataDictionaryDao;
import com.billow.system.init.IStartLoading;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化数据字典
 *
 * @author liuyongtao
 * @create 2019-07-29 13:59
 */
@Component
public class InitDictionary implements IStartLoading {

    private final static String key = RedisCst.COMM_DICTIONARY;

    @Autowired
    private DataDictionaryDao dataDictionaryDao;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean init() {
        List<DataDictionaryPo> dataDictionaryPos = dataDictionaryDao.findAll();
        redisUtils.setObj(key, ConvertUtils.convertIgnoreBase(dataDictionaryPos,DataDictionaryPo.class));
        return true;
    }
}
