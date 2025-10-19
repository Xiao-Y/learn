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
@TableName("sys_white_list")
@Schema(title = "WhiteListPo对象", description="")
public class WhiteListPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("ip")
    private String ip;

    @TableField("mark")
    private String mark;

    @TableField("module")
    private String module;

    @TableField("port")
    private String port;


}
