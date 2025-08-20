package com.billow.order.pojo.build;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@Data
@Accessors(chain = true)
public class CompanyAddressBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "地址名称")
    private String addressName;

    @Schema(title = "默认发货地址：0->否；1->是")
    private Integer sendStatus;

    @Schema(title = "是否默认收货地址：0->否；1->是")
    private Integer receiveStatus;

    @Schema(title = "收发货人姓名")
    private String name;

    @Schema(title = "收货人电话")
    private String phone;

    @Schema(title = "省/直辖市")
    private String province;

    @Schema(title = "市")
    private String city;

    @Schema(title = "区")
    private String region;

    @Schema(title = "详细地址")
    private String detailAddress;


}
