package com.billow.system.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * VIEW 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2025-10-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MytasklistSearchParam extends BasePage implements Serializable {

    @Column("assignee")
    private String assignee;

    @Schema(title = "用户分组")
    private String groupId;

}
