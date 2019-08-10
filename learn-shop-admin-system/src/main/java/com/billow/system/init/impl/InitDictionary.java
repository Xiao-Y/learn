package com.billow.system.init.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.DataDictionaryDao;
import com.billow.system.init.IStartLoading;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 初始化数据字典
 *
 * @author liuyongtao
 * @create 2019-07-29 13:59
 */
@Slf4j
@Component
public class InitDictionary implements IStartLoading {

    private final static String key = RedisCst.COMM_DICTIONARY;

    @Autowired
    private DataDictionaryDao dataDictionaryDao;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean init() {
        log.info("======== start init Dictionary....");
        List<DataDictionaryPo> dataDictionaryPos = dataDictionaryDao.findAll();
        // 以 systemModule 分组
        Map<String, List<DataDictionaryPo>> collect = dataDictionaryPos.stream()
                .collect(Collectors.groupingBy(DataDictionaryPo::getSystemModule));
        // 分别保存到 redis 中
        for (Map.Entry<String, List<DataDictionaryPo>> entry : collect.entrySet()) {
            String systemModule = entry.getKey();
            List<DataDictionaryPo> pos = entry.getValue();
            pos.sort(Comparator.nullsLast(Comparator.comparing(DataDictionaryPo::getFieldOrder)));

            redisUtils.setObj(key + systemModule, ConvertUtils.convertIgnoreBase(pos, DataDictionaryPo.class));
        }
        log.info("======== end init Dictionary....");
        return true;
    }
}
