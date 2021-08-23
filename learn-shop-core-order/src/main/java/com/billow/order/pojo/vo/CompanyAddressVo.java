package com.billow.order.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-23
 */
@Data
@Accessors(chain = true)
public class CompanyAddressVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址名称")
    private String addressName;

    @ApiModelProperty(value = "默认发货地址：0->否；1->是")
    private Integer sendStatus;

    @ApiModelProperty(value = "是否默认收货地址：0->否；1->是")
    private Integer receiveStatus;

    @ApiModelProperty(value = "收发货人姓名")
    private String name;

    @ApiModelProperty(value = "收货人电话")
    private String phone;

    @ApiModelProperty(value = "省/直辖市")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String region;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;


}
