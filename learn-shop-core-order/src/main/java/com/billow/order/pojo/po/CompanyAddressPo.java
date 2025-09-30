package com.billow.order.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;

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
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_company_address")
@Schema(title = "CompanyAddressPo对象", description="")
public class CompanyAddressPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "地址名称")
    @TableField("address_name")
    private String addressName;

    @Schema(title = "默认发货地址：0->否；1->是")
    @TableField("send_status")
    private Integer sendStatus;

    @Schema(title = "是否默认收货地址：0->否；1->是")
    @TableField("receive_status")
    private Integer receiveStatus;

    @Schema(title = "收发货人姓名")
    @TableField("name")
    private String name;

    @Schema(title = "收货人电话")
    @TableField("phone")
    private String phone;

    @Schema(title = "省/直辖市")
    @TableField("province")
    private String province;

    @Schema(title = "市")
    @TableField("city")
    private String city;

    @Schema(title = "区")
    @TableField("region")
    private String region;

    @Schema(title = "详细地址")
    @TableField("detail_address")
    private String detailAddress;


}
