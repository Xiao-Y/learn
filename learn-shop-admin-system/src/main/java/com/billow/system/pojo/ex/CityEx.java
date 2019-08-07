package com.billow.system.pojo.ex;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuyongtao
 * @create 2019-08-04 16:49
 */
@Data
public class CityEx implements Serializable {

    @ApiModelProperty("城市 id")
    private String cityId;

    @ApiModelProperty("上级城市 id")
    private String parentCityId;

    @ApiModelProperty("城市名称 id")
    private String name;

    @ApiModelProperty("下级城市信息")
    private List<CityEx> children;
}
