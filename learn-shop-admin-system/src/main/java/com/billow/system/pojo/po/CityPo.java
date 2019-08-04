package com.billow.system.pojo.po;

import com.billow.common.base.pojo.BasePo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 中国省市区
 *
 * @author liuyongtao
 * @create 2019-08-04 10:52
 */
@Data
@Entity
@Table(name = "sys_city")
public class CityPo extends BasePo {

    // 城市 id
    private String cityId;
    // 上级城市id
    private String parentCityId;
    // 城市等级
    private String levelType;
    // 城市代码
    private String cityCode;
    // 城市名称
    private String name;
    // 城市缩写
    private String shortName;
    // 城市拼音
    private String pinYin;
    // 城市完整名称
    private String mergerName;
    // 邮编
    private String zipCode;
    // 纬度
    private String lat;
    // 经度
    private String lng;
}
