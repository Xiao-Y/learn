package com.billow.system.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.system.pojo.search.ScheduleJobLogSearchParam;
import com.billow.system.pojo.po.ScheduleJobLogPo;
import com.billow.system.service.ScheduleJobLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author billow
 * @since 2025-10-19
 * @version v2.0
 */
@Slf4j
@Tag(name = "ScheduleJobLogApi",description =  "")
@RestController
@RequestMapping("/scheduleJobLogApi")
public class ScheduleJobLogApi extends HighLevelApi<ScheduleJobLogService, ScheduleJobLogPo, ScheduleJobLogSearchParam> {

}
