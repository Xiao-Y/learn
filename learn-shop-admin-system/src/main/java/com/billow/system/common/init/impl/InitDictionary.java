package com.billow.system.common.init.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.common.init.IStartLoading;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Autowired
    private RedisUtils redisUtils;
    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;

    @Override
    public boolean init() {
        log.info("======== start init Dictionary....");
        executorService.execute(() -> {
            List<DataDictionaryPo> dataDictionaryPos = dataDictionaryService.list();
            // 以 getFieldType 分组
            Map<String, List<DataDictionaryPo>> collect = dataDictionaryPos.stream()
                    .collect(Collectors.groupingBy(DataDictionaryPo::getFieldType));
            // 排序
            for (Map.Entry<String, List<DataDictionaryPo>> entry : collect.entrySet()) {
                List<DataDictionaryPo> pos = entry.getValue();
                pos.sort(Comparator.nullsLast(Comparator.comparing(DataDictionaryPo::getFieldOrder)));
            }
            redisUtils.setHash(RedisCst.COMM_DICTIONARY_FIELD_TYPE, collect);
            log.info("======== end init Dictionary....");
        });
        return true;
    }
}
