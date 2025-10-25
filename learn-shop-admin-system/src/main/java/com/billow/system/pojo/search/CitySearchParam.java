package com.billow.system.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import com.mybatisflex.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2025-10-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CitySearchParam extends BasePage implements Serializable {

    // 有效标志
    @Column(value = "valid_ind")
    private Boolean validInd;

    @Column("city_id")
    private String cityId;


    @Column("parent_city_id")
    private String parentCityId;

}
