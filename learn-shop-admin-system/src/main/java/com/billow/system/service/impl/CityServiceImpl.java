package com.billow.system.service.impl;

import com.billow.common.redis.RedisUtils;
import com.billow.system.dao.CityDao;
import com.billow.system.pojo.ex.CityEx;
import com.billow.system.pojo.po.CityPo;
import com.billow.system.service.CityService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
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
        List<List<CityPo>> cityPoss = redisUtils.getHashAllValue(RedisCst.COMM_CITY);
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
            redisUtils.setHash(RedisCst.COMM_CITY, map);
            Set<CityEx> set = new HashSet<>();
            set.addAll(ConvertUtils.convertIgnoreBase(cityPos, CityEx.class));
            return set;
        }
        Set<CityEx> set = new HashSet<>();
        cityPoss.stream().forEach(f -> {
            List<CityEx> cityExes = ConvertUtils.convertIgnoreBase(f, CityEx.class);
            set.addAll(cityExes);
        });
        return set;
    }

    @Override
    public List<CityEx> findCityByParentCityId(String cityId) {
        // 新建一个list来存放一级菜单
        List<CityEx> tree = new ArrayList<>();

        CityPo cityPo = cityDao.findByCityId(cityId);
        if (cityPo == null) {
            return tree;
        }
        return this.setCityChildren(tree, cityPo);
    }

    private List<CityEx> setCityChildren(List<CityEx> tree, CityPo cityPo) {
        CityEx cityEx = ConvertUtils.convertIgnoreBase(cityPo, CityEx.class);
        List<CityPo> cityLowerLevel = this.findCityLowerLevel(cityPo.getCityId());
        List<CityEx> cityExs = ConvertUtils.convertIgnoreBase(cityLowerLevel, CityEx.class);
        cityEx.setChildren(cityExs);
        tree.add(cityEx);
        if (CollectionUtils.isNotEmpty(cityLowerLevel)) {
            for (CityPo po : cityLowerLevel) {
                this.setCityChildren(tree, po);
            }
        }
        return tree;
    }

//    @Override
//    public List<CityEx> findCityByParentCityId(String cityId) {
//        // 新建一个list来存放一级菜单
//        List<CityEx> tree = new ArrayList<>();
//        // 查询所有
//        List<CityEx> cityVos = this.findCity();
//        // 将所有的数据，以键值对的形式装入map中
//        Map<String, CityEx> map = new HashMap<>();
//        for (CityEx cityVo : cityVos) {
//            map.put(cityVo.getCityId(), cityVo);
//        }
//
//        for (CityEx cityVo : cityVos) {
//            // 如果id是父级的话就放入 tree 中
//            if (cityVo.getParentCityId().equals(cityId)) {
//                tree.add(cityVo);
//            } else {
//                // 子级通过父id获取到父级的类型
//                CityEx parent = map.get(cityVo.getParentCityId());
//                if (parent == null) {
//                    continue;
//                }
//                // 父级获得子级，再将子级放到对应的父级中
//                List<CityEx> children = parent.getChildren();
//                if (ToolsUtils.isEmpty(children)) {
//                    parent.setChildren(new ArrayList<>());
//                }
//                parent.getChildren().add(cityVo);
//                // 添加了新对象，所以要在 put 一次
//                map.put(cityVo.getParentCityId(), parent);
//            }
//        }
//        return tree;
//    }

    @Override
    public List<CityEx> getCityLowerLevel(String cityId) {
        List<CityPo> cityPos = findCityLowerLevel(cityId);
        return ConvertUtils.convertIgnoreBase(cityPos, CityEx.class);
    }

    /**
     * 查询城市的下级
     *
     * @param cityId
     * @return {@link List< CityPo>}
     * @author liuyongtao
     * @since 2021-1-28 15:54
     */
    private List<CityPo> findCityLowerLevel(String cityId) {
        List<CityPo> cityPos = redisUtils.getHash(RedisCst.COMM_CITY, cityId);
        if (CollectionUtils.isEmpty(cityPos)) {
            cityPos = cityDao.findByParentCityIdIsAndValidIndIsTrue(cityId);
            redisUtils.setHash(RedisCst.COMM_CITY, cityId, cityPos);
        }
        return cityPos;
    }
}
