package com.billow.product.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。
 * </p>
 *
 * @author billow
 * @since 2021-08-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("sms_seckill")
@Schema(title = "SeckillPo对象", description="限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。")
public class SeckillPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "标题")
    @Column("title")
    private String title;

    @Schema(title = "开始日期")
    @Column("start_date")
    private LocalDateTime startDate;

    @Schema(title = "结束日期")
    @Column("end_date")
    private LocalDateTime endDate;

    @Schema(title = "上下线状态")
    @Column("status")
    private Integer status;


}
