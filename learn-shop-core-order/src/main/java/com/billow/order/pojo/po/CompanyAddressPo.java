package com.billow.order.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
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
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_company_address")
@ApiModel(value="CompanyAddressPo对象", description="")
public class CompanyAddressPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址名称")
    @TableField("address_name")
    private String addressName;

    @ApiModelProperty(value = "默认发货地址：0->否；1->是")
    @TableField("send_status")
    private Integer sendStatus;

    @ApiModelProperty(value = "是否默认收货地址：0->否；1->是")
    @TableField("receive_status")
    private Integer receiveStatus;

    @ApiModelProperty(value = "收发货人姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "收货人电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "省/直辖市")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区")
    @TableField("region")
    private String region;

    @ApiModelProperty(value = "详细地址")
    @TableField("detail_address")
    private String detailAddress;


}
