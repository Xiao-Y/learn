package com.billow.system.init.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.DataDictionaryDao;
import com.billow.system.init.IStartLoading;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
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

    private final static String key1 = RedisCst.COMM_DICTIONARY_SYS_MODULE;
    private final static String key2 = RedisCst.COMM_DICTIONARY_FIELD_TYPE;
    private final static String key3 = RedisCst.COMM_DICTIONARY_SYS_MODULE_LIST;

    @Autowired
    private DataDictionaryDao dataDictionaryDao;
    @Autowired
    private RedisUtils redisUtils;
    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;

    @Override
    public boolean init() {
        log.info("======== start init Dictionary....");
        executorService.execute(() -> {
            List<DataDictionaryPo> dataDictionaryPos = dataDictionaryDao.findAll();
            // 以 systemModule 分组
            Map<String, List<DataDictionaryPo>> collect = dataDictionaryPos.stream()
                    .collect(Collectors.groupingBy(DataDictionaryPo::getSystemModule));
            // 保存field type 到 redis 中
            List<String> fieldTypes = dataDictionaryDao.findFieldType();
            redisUtils.setObj(key2, fieldTypes);
            // 保存 system module 到 redis 中
            Set<String> sysModuleList = collect.keySet();
            redisUtils.setObj(key3, sysModuleList);
            // 按 SystemModule 分别保存到 redis 中
            for (Map.Entry<String, List<DataDictionaryPo>> entry : collect.entrySet()) {
                String systemModule = entry.getKey();
                List<DataDictionaryPo> pos = entry.getValue();
                pos.sort(Comparator.nullsLast(Comparator.comparing(DataDictionaryPo::getFieldOrder)));

                redisUtils.setObj(key1 + systemModule, ConvertUtils.convertIgnoreBase(pos, DataDictionaryPo.class));
            }
            log.info("======== end init Dictionary....");
        });
        return true;
    }
}
