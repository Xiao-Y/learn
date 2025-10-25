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
public class DataDictionarySearchParam extends BasePage implements Serializable {

    // 有效标志
    @Column(value = "valid_ind")
    private Boolean validInd;

    @Column("field_type")
    private String fieldType;

    @Column("field_value")
    private String fieldValue;

    @Column("field_display")
    private String fieldDisplay;

    @Column("system_module")
    private String systemModule;

    @Column("description")
    private String description;

}
