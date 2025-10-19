package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2025-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_city")
@Schema(title = "CityPo对象", description="")
public class CityPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("city_id")
    private String cityId;

    @TableField("parent_city_id")
    private String parentCityId;

    @TableField("level_type")
    private String levelType;

    @Schema(title = "地市code")
    @TableField("city_code")
    private String cityCode;

    @TableField("name")
    private String name;

    @TableField("short_name")
    private String shortName;

    @TableField("pin_yin")
    private String pinYin;

    @TableField("merger_name")
    private String mergerName;

    @Schema(title = "邮编")
    @TableField("zip_code")
    private String zipCode;

    @TableField("lat")
    private String lat;

    @TableField("lng")
    private String lng;


}
