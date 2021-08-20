package com.billow.order.pojo.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 秒杀成功明细表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
@Data
public class SuccessKilledVo implements Serializable {

    // 主键id
    private Long id;

    @ApiModelProperty(value = "用户code")
    private String usercode;

    @ApiModelProperty(value = "秒杀商品ID")
    private Long seckillId;

    @ApiModelProperty(value = "秒杀状态标识:-0:无效 1:成功 2:已付款 3:已发货")
    private Integer killState;

    // 创建人
    private String creatorCode;

    // 创建人
    private String updaterCode;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;

    // 有效标志
    private Boolean validInd;
}
