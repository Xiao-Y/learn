package com.billow.system.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-12-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserSearchParam extends BasePage implements Serializable {
    @Schema(title = "用户code")
    private String usercode;
}
