package com.billow.system.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("sys_apply_info")
@ApiModel(value = "ApplyInfoPo对象", description = "")
public class ApplyInfoPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("apply_data")
    private String applyData;

    @TableField("apply_type")
    private String applyType;

    @TableField("apply_user_code")
    private String applyUserCode;

    @TableField("is_end")
    private Boolean isEnd;

    @TableField("proc_def_id")
    private String procDefId;

    @TableField("proc_inst_id")
    private String procInstId;

    @TableField("vo_clazz")
    private String voClazz;


}
