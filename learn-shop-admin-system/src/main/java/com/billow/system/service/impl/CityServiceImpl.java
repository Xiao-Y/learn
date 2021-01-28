package com.billow.system.service.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.CityDao;
import com.billow.system.pojo.ex.CityEx;
import com.billow.system.pojo.po.CityPo;
import com.billow.system.pojo.vo.CityVo;
import com.billow.system.service.CityService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 省市区
 *
 * @author liuyongtao
 * @create 2019-08-04 13:50
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public Set<CityEx> findCity() {
        Set<CityEx> set = new HashSet<>();
        List<List<CityPo>> cityPoss = redisUtils.getHashAllValue(RedisCst.COMM_CITY_TREE);
        if (CollectionUtils.isEmpty(cityPoss)) {
            List<CityPo> cityPos = cityDao.findByValidIndIsTrue();
            // 添加到缓存
            Map<String, List<CityPo>> map = new HashMap<>();
            cityPos.stream().forEach(f -> {
                List<CityPo> collect = cityPos.stream()
                        .filter(ff -> ff.getParentCityId().equals(f.getCityId()))
                        .collect(Collectors.toList());
                map.put(f.getCityId(), collect);
            });
            if (MapUtils.isNotEmpty(map)) {
                redisUtils.setHash(RedisCst.COMM_CITY_TREE, map);
            }
            set.addAll(ConvertUtils.convertIgnoreBase(cityPos, CityEx.class));
            return set;
        }
        cityPoss.stream()
                .filter(f -> CollectionUtils.isNotEmpty(f))
                .forEach(f -> {
                    List<CityEx> cityExes = ConvertUtils.convertIgnoreBase(f, CityEx.class);
                    set.addAll(cityExes);
                });
        return set;
    }

    @Override
    public CityVo findByCityId(String cityId) {
        CityPo po = redisUtils.getHash(RedisCst.COMM_CITY_ONE, cityId);
        if (po == null) {
            po = cityDao.findByCityId(cityId);
            if (po != null) {
                redisUtils.setHash(RedisCst.COMM_CITY_ONE, cityId, po);
            }
        }
        return ConvertUtils.convertIgnoreBase(po, CityVo.class);
    }

    @Override
    public CityEx findCityByParentCityId(String cityId) {
        CityEx cityEx = new CityEx();
        CityPo cityPo = cityDao.findByCityId(cityId);
        if (cityPo == null) {
            return cityEx;
        }
        cityEx = ConvertUtils.convertIgnoreBase(cityPo, CityEx.class);
        // 递归查询下级
        this.setCityChildren(cityEx);
        return cityEx;
    }

    @Override
    public List<CityEx> getCityLowerLevel(String cityId) {
        List<CityPo> cityPos = findCityLowerLevel(cityId, true);
        return ConvertUtils.convertIgnoreBase(cityPos, CityEx.class);
    }

    /**
     * 查询城市的下级
     *
     * @param cityId
     * @param isCheckDb 缓存为空时，是否查询数据库
     * @return {@link List< CityPo>}
     * @author liuyongtao
     * @since 2021-1-28 15:54
     */
    private List<CityPo> findCityLowerLevel(String cityId, boolean isCheckDb) {
        List<CityPo> cityPos = redisUtils.getHash(RedisCst.COMM_CITY_TREE, cityId);
        if (CollectionUtils.isEmpty(cityPos) && isCheckDb) {
            cityPos = cityDao.findByParentCityIdIsAndValidIndIsTrue(cityId);
            if (CollectionUtils.isNotEmpty(cityPos)) {
                redisUtils.setHash(RedisCst.COMM_CITY_TREE, cityId, cityPos);
            }
        }
        return cityPos;
    }

    /**
     * 递归查询下级
     *
     * @param cityEx
     * @author liuyongtao
     * @since 2021-1-28 19:41
     */
    private void setCityChildren(CityEx cityEx) {
        List<CityPo> cityLowerLevel = this.findCityLowerLevel(cityEx.getCityId(), false);
        List<CityEx> cityExs = ConvertUtils.convertIgnoreBase(cityLowerLevel, CityEx.class);
        if (CollectionUtils.isNotEmpty(cityExs)) {
            for (CityEx po : cityExs) {
                this.setCityChildren(po);
            }
        }
        cityEx.setChildren(cityExs);
    }
}
