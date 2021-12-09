package com.billow.system.common.init.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.billow.system.dao.CityDao;
import com.billow.system.common.init.IStartLoading;
import com.billow.system.pojo.po.CityPo;
import com.billow.tools.constant.RedisCst;
import com.bilow.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * 初始化城市信息
 *
 * @author liuyongtao
 * @since 2021-1-28 11:56
 */
@Slf4j
@Component
public class InitCity implements IStartLoading {

    @Autowired
    private CityDao cityDao;
    @Autowired
    private RedisUtils redisUtils;
    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;

    @Override
    public boolean init() {
        log.info("======== start init City....");
        executorService.execute(() -> {
            // 构建tree 结构的
            LambdaQueryWrapper<CityPo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(CityPo::getValidInd, true);
            List<CityPo> cityPos = cityDao.selectList(wrapper);
            Map<String, List<CityPo>> map = new HashMap<>();
            cityPos.stream().forEach(f -> {
                List<CityPo> collect = cityPos.stream()
                        .filter(ff -> ff.getParentCityId().equals(f.getCityId()))
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    map.put(f.getCityId(), collect);
                }
            });
            redisUtils.setHash(RedisCst.COMM_CITY_TREE, map);
            // 构建map结构
            Map<String, CityPo> collect = cityPos.stream().collect(Collectors.toMap(CityPo::getCityId, v -> v));
            if (MapUtils.isNotEmpty(collect)) {
                redisUtils.setHash(RedisCst.COMM_CITY_ONE, collect);
            }
            log.info("======== end init City....");
        });
        return true;
    }
}
