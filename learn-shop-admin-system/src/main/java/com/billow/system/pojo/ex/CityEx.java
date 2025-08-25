package com.billow.system.pojo.ex;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuyongtao
 * @create 2019-08-04 16:49
 */
@Data
@EqualsAndHashCode
public class CityEx implements Serializable {

    @Schema(title = "城市 id")
    private String cityId;

    @Schema(title = "上级城市 id")
    private String parentCityId;

    @Schema(title = "城市名称")
    private String name;

    @Schema(title = "级别")
    private String levelType;

    @Schema(title = "下级城市信息")
    private List<CityEx> children;
}
