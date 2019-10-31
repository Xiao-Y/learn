package com.billow.system.pojo.po;


import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ip白名单
 *
 * @author liuyongtao
 * @create 2018-05-19 14:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_white_list")
public class WhiteListPo extends BasePo implements Serializable {

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("模块")
    private String module;

    @ApiModelProperty("端口")
    private String port;

    @ApiModelProperty("备注")
    private String mark;
}
