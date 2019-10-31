package com.billow.user.pojo.po;


import com.billow.jpa.base.pojo.BasePo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 城市模型
 *
 * @author liuyongtao
 * @create 2018-02-08 10:27
 */
@Entity
@Table(name = "t_city")
public class CityPo extends BasePo implements Serializable {

    private Long population;
    private String name;
    @Column(name = "countryCode")
    private String countrycode;
    private String district;

    public Long getPopulation() {
        return population;
    }

    public CityPo setPopulation(Long population) {
        this.population = population;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityPo setName(String name) {
        this.name = name;
        return this;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public CityPo setCountrycode(String countrycode) {
        this.countrycode = countrycode;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public CityPo setDistrict(String district) {
        this.district = district;
        return this;
    }

    @Override
    public String toString() {
        return "CityPo{" +
                "population=" + population +
                ", name='" + name + '\'' +
                ", countrycode='" + countrycode + '\'' +
                ", district='" + district + '\'' +
                "} " + super.toString();
    }
}
