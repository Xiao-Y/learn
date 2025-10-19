package com.billow.product.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalTime;

/**
 * <p>
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。
 * </p>
 *
 * @author billow
 * @since 2021-08-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("sms_seckill_session")
@Schema(title = "SeckillSessionPo对象", description="限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。")
public class SeckillSessionPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "场次名称")
    @Column("name")
    private String name;

    @Schema(title = "每日开始时间")
    @Column("start_time")
    private LocalTime startTime;

    @Schema(title = "每日结束时间")
    @Column("end_time")
    private LocalTime endTime;

    @Schema(title = "启用状态：0->不启用；1->启用")
    @Column("status")
    private Integer status;


}
