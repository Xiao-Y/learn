package com.billow.promotion.pojo.cache;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-08-27
 */
@Data
@Accessors(chain = true)
public class SeckillCacheDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键id")
    private Long id;

    @Schema(title = "标题")
    private String title;

    @Schema(title = "开始日期")
    private LocalDateTime startDate;

    @Schema(title = "结束日期")
    private LocalDateTime endDate;

    @Schema(title = "上下线状态")
    private Integer status;


}
