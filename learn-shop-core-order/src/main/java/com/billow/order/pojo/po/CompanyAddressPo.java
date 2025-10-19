package com.billow.order.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
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
@Table("oms_company_address")
@Schema(title = "CompanyAddressPo对象", description="")
public class CompanyAddressPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "地址名称")
    @Column("address_name")
    private String addressName;

    @Schema(title = "默认发货地址：0->否；1->是")
    @Column("send_status")
    private Integer sendStatus;

    @Schema(title = "是否默认收货地址：0->否；1->是")
    @Column("receive_status")
    private Integer receiveStatus;

    @Schema(title = "收发货人姓名")
    @Column("name")
    private String name;

    @Schema(title = "收货人电话")
    @Column("phone")
    private String phone;

    @Schema(title = "省/直辖市")
    @Column("province")
    private String province;

    @Schema(title = "市")
    @Column("city")
    private String city;

    @Schema(title = "区")
    @Column("region")
    private String region;

    @Schema(title = "详细地址")
    @Column("detail_address")
    private String detailAddress;


}
