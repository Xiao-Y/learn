package com.billow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.system.dao.CityDao;
import com.billow.system.pojo.ex.CityEx;
import com.billow.system.pojo.po.CityPo;
import com.billow.system.pojo.vo.CityVo;
import com.billow.system.service.CityService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import com.billow.redis.util.RedisUtils;
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
public class CityServiceImpl extends ServiceImpl<CityDao, CityPo> implements CityService {

    @Autowired
    private CityDao cityDao;
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public Set<CityEx> findCity() {
        Set<CityEx> set = new HashSet<>();
        List<CityPo> cityPoss = redisUtils.getHashAllValue(RedisCst.COMM_CITY_TREE, CityPo.class);
        if (CollectionUtils.isEmpty(cityPoss)) {
            LambdaQueryWrapper<CityPo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(CityPo::getValidInd, true);
            List<CityPo> cityPos = cityDao.selectList(wrapper);
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
                .filter(Objects::nonNull)
                .forEach(f -> {
                    CityEx cityExe = ConvertUtils.convertIgnoreBase(f, CityEx.class);
                    set.add(cityExe);
                });
        return set;
    }

    @Override
    public CityVo findByCityId(String cityId) {
        CityPo po = redisUtils.getHashObj(RedisCst.COMM_CITY_ONE, cityId, CityPo.class);
        if (po == null) {
            LambdaQueryWrapper<CityPo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(CityPo::getCityId, cityId);
            List<CityPo> list = this.list(wrapper);
            if (ToolsUtils.isNotEmpty(list)) {
                po = list.get(0);
                redisUtils.setHash(RedisCst.COMM_CITY_ONE, cityId, po);
            }
        }
        return ConvertUtils.convertIgnoreBase(po, CityVo.class);
    }

    @Override
    public CityEx findCityByParentCityId(String cityId) {
        CityEx cityEx = new CityEx();
        CityPo cityPo = this.findByCityId(cityId);
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
        List<CityPo> cityPos = redisUtils.getHash(RedisCst.COMM_CITY_TREE, cityId, CityPo.class);
        if (CollectionUtils.isEmpty(cityPos) && isCheckDb) {
            LambdaQueryWrapper<CityPo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(CityPo::getParentCityId, cityId)
                    .eq(CityPo::getValidInd, true);
            cityPos = this.list(wrapper);
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
