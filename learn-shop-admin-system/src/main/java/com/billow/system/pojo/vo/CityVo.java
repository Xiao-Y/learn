package com.billow.system.pojo.vo;

import com.billow.system.pojo.ex.CityEx;
import com.billow.system.pojo.po.CityPo;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "下级城市信息")
    private List<CityVo> children;
}
