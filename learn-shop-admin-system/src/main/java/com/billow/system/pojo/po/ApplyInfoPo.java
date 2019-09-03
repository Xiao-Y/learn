package com.billow.system.pojo.po;

import com.billow.common.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 申请信息
 *
 * @author liuyongtao
 * @create 2019-09-02 17:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_apply_info")
public class ApplyInfoPo extends BasePo implements Serializable {

    @ApiModelProperty("流程定义ID")
    private String procDefId;

    @ApiModelProperty("流程实例定义")
    private String procInstId;

    @ApiModelProperty("流程是否结束")
    private Boolean isEnd;

    @ApiModelProperty("申请数据，JSON 格式")
    private String applyData;

    @ApiModelProperty("申请类型：")
    private String applyType;

    @ApiModelProperty("申请人CODE")
    private String applyUserCode;

    @ApiModelProperty("JSON 转换格式，暂时不用")
    private String voClazz;
}
