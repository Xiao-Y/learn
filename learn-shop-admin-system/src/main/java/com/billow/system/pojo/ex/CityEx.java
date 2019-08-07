package com.billow.system.pojo.ex;

import lombok.Data;

import java.util.List;

/**
 * @author liuyongtao
 * @create 2019-08-04 16:49
 */
@Data
public class CityEx {
    // 城市 id
    private String cityId;
    // 上级城市id
    private String parentCityId;
    // 城市名称
    private String name;

    private List<CityEx> children;
}
