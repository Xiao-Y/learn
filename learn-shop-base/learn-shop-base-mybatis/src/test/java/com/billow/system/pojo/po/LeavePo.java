package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("u_leave")
@Schema(title = "LeavePo对象", description="")
public class LeavePo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("end_date")
    private LocalDateTime endDate;

    @TableField("reason")
    private String reason;

    @TableField("start_date")
    private LocalDateTime startDate;


}
