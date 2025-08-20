package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@Data
@Accessors(chain = true)
public class SeckillBuildParam extends BasePo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "标题")
    private String title;

    @Schema(title = "开始日期")
    private LocalDateTime startDate;

    @Schema(title = "结束日期")
    private LocalDateTime endDate;

    @Schema(title = "上下线状态")
    private Integer status;


}
