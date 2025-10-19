package com.billow.system.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

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
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("sys_city")
@Schema(title = "CityPo对象", description = "")
public class CityPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Column("city_id")
    private String cityId;

    @Column("parent_city_id")
    private String parentCityId;

    @Column("level_type")
    private String levelType;

    @Schema(title = "地市code")
    @Column("city_code")
    private String cityCode;

    @Column("name")
    private String name;

    @Column("short_name")
    private String shortName;

    @Column("pin_yin")
    private String pinYin;

    @Column("merger_name")
    private String mergerName;

    @Schema(title = "邮编")
    @Column("zip_code")
    private String zipCode;

    @Column("lat")
    private String lat;

    @Column("lng")
    private String lng;


}
