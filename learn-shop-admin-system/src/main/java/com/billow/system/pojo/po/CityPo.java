package com.billow.system.pojo.po;

import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 中国省市区
 *
 * @author liuyongtao
 * @create 2019-08-04 10:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_city")
public class CityPo extends BasePo implements Serializable {

    @ApiModelProperty("城市 id")
    private String cityId;

    @ApiModelProperty("上级城市id")
    private String parentCityId;

    @ApiModelProperty("城市等级")
    private String levelType;
    @ApiModelProperty("城市代码")
    private String cityCode;

    @ApiModelProperty("城市名称")
    private String name;

    @ApiModelProperty("城市缩写")
    private String shortName;

    @ApiModelProperty("城市拼音")
    private String pinYin;

    @ApiModelProperty("城市完整名称")
    private String mergerName;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("纬度")
    private String lat;

    @ApiModelProperty("经度")
    private String lng;
}
