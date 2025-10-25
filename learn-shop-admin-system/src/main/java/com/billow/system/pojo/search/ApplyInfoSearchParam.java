package com.billow.system.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import com.mybatisflex.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2025-10-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ApplyInfoSearchParam extends BasePage implements Serializable {


    private String assignee;
    private String taskId;
    private String taskName;
    private String status;
    private String isEndStatus;
    // 是否挂起,0-不存在，1-活动，2-挂起
    private int suspensionStatus;

    @Column("apply_user_code")
    private String applyUserCode;

}
