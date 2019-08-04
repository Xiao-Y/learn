package com.billow.system.service.impl;

import com.billow.system.dao.CityDao;
import com.billow.system.pojo.po.CityPo;
import com.billow.system.pojo.vo.CityVo;
import com.billow.system.service.CityService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.tools.internal.xjc.reader.Ring.add;

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

    @Override
    public List<CityVo> findCityByParentCityId(String parentCityId) {

        // 新建一个list来存放一级菜单
        List<CityVo> tree = new ArrayList<>();

        List<CityPo> cityPos = cityDao.findAll();
        List<CityVo> cityVos = ConvertUtils.convertIgnoreBase(cityPos, CityVo.class);

        // 将所有的数据，以键值对的形式装入map中
        Map<String, CityVo> map = new HashMap<>();
        for (CityVo cityVo : cityVos) {
            map.put(cityVo.getCityId(), cityVo);
        }

        for (CityVo cityVo : cityVos) {
            // 如果id是父级的话就放入 tree 中
            if (cityVo.getParentCityId().equals(parentCityId)) {
                tree.add(cityVo);
            } else {
                // 子级通过父id获取到父级的类型
                CityVo parent = map.get(cityVo.getParentCityId());
                if (parent == null) {
                    continue;
                }
                // 父级获得子级，再将子级放到对应的父级中
                List<CityVo> children = parent.getChildren();
                if (ToolsUtils.isEmpty(children)) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(cityVo);
                // 添加了新对象，所以要在 put 一次
                map.put(cityVo.getParentCityId(), parent);
            }
        }
        return tree;
    }
}
