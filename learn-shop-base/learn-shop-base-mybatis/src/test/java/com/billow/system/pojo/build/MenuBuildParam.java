package com.billow.system.pojo.build;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2022-01-04
 */
@Data
@Accessors(chain = true)
public class MenuBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "唯一")
    private String menuCode;

    private String menuName;

    private Long pid;

    private Boolean display;

    private String icon;

    private String description;

    private Double sortField;


}
