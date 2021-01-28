package com.billow.system.pojo.vo;

import com.billow.system.pojo.ex.CityEx;
import com.billow.system.pojo.po.CityPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 中国省市区
 *
 * @author liuyongtao
 * @create 2019-08-04 11:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CityVo extends CityPo implements Serializable {

    @ApiModelProperty("下级城市信息")
    private List<CityVo> children;
}
