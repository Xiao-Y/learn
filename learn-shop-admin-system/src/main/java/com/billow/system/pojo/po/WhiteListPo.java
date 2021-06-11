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
@TableName("sys_white_list")
@ApiModel(value="WhiteListPo对象", description="")
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
