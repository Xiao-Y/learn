package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.job.common.CustomPage;
import com.billow.job.pojo.ex.TestRunCronEx;
import com.billow.job.pojo.po.ScheduleJobLogPo;
import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.pojo.vo.ScheduleJobLogVo;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.service.CoreAutoTaskService;
import com.billow.job.service.ScheduleJobLogService;
import com.billow.job.service.ScheduleJobService;
import com.billow.job.util.TaskUtils;
import com.billow.tools.utlis.ToolsUtils;
import com.billow.tools.utlis.UserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 核心自动任务控制类
 *
 * @author liuyongtao
 * @create 2018-07-02 9:06
 */
@Api("核心自动任务控制类")
@RestController
@RequestMapping("/coreAutoTaskApi")
public class CoreAutoTaskApi extends BaseApi {
    private static final Logger logger = LoggerFactory.getLogger(CoreAutoTaskApi.class);

    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;
    @Autowired
    private CoreAutoTaskService coreAutoTaskService;
    @Autowired
    private UserTools userTools;

    @ApiOperation("查询自动任务列表")
    @PostMapping("/findAutoTask")
    public CustomPage<ScheduleJobPo> findAutoTask(@RequestBody ScheduleJobVo scheduleJobVo) {
        CustomPage<ScheduleJobPo> jods = scheduleJobService.findAll(scheduleJobVo);
        return jods;
    }

    @ApiOperation("启用、停止、禁用自动任务")
    @ApiImplicitParams({@ApiImplicitParam(dataType = "Integer", name = "jobId", value = "自动任务id", required = true),
            @ApiImplicitParam(dataType = "String", name = "jobStatus", value = "任务状态，0-停止，1-启用", required = true)})
    @PutMapping(value = {"/updateJobStatus/{jobId}/{jobStatus}", "/updateJobValidInd/{jobId}/{validInd}"})
    public void updateJobStatus(@PathVariable("jobId") Long jobId,
                                @PathVariable(value = "jobStatus", required = false) String jobStatus,
                                @PathVariable(value = "validInd", required = false) Boolean validInd) throws Exception {
        // 不能同时为空
        if (ToolsUtils.isEmpty(jobStatus) && validInd == null) {
            return;
        }
        ScheduleJobVo dto = new ScheduleJobVo();
        dto.setId(jobId);
        dto.setJobStatus(jobStatus);
        dto.setValidInd(validInd);
        dto.setUpdaterCode(userTools.getCurrentUserCode());
        coreAutoTaskService.updateJobStatus(dto);
    }

    @ApiOperation("根据任务id,删除自动任务")
    @ApiParam(name = "jobId", value = "自动任务id")
    @DeleteMapping("/deleteAutoTask/{jobId}")
    public void deleteAutoTask(@PathVariable("jobId") Long jobId) throws Exception {
        coreAutoTaskService.deleteAutoTask(jobId);
    }

    @ApiOperation("保存自动任务")
    @PostMapping("/saveAutoTask")
    public ScheduleJobVo saveAutoTask(@RequestBody ScheduleJobVo scheduleJobVo) throws Exception {
        scheduleJobVo.setCreatorCode(userTools.getCurrentUserCode());
        coreAutoTaskService.saveAutoTask(scheduleJobVo);
        return scheduleJobVo;
    }

    @ApiOperation("立即执行自动任务")
    @PostMapping("/immediateExecutionTask")
    public ScheduleJobVo immediateExecutionTask(@RequestBody ScheduleJobVo scheduleJobVo) throws Exception {
        coreAutoTaskService.immediateExecutionTask(scheduleJobVo);
        return scheduleJobVo;
    }

    @ApiOperation("校验自动任务添加、修改时参数的设置")
    @PostMapping("/checkAutoTask")
    public ScheduleJobVo checkAutoTask(@RequestBody ScheduleJobVo scheduleJobVo) throws Exception {
        coreAutoTaskService.checkAutoTask(scheduleJobVo);
        return scheduleJobVo;
    }

    @ApiOperation("测试Cron表达式下次运行的时间")
    @PostMapping("/testRunCron")
    public List<String> testRunCron(@RequestBody TestRunCronEx testRunCronEx) {
        logger.info("cron:{}", testRunCronEx.getCron());
        return TaskUtils.runTime(testRunCronEx.getCron(), testRunCronEx.getTimes());
    }

    @ApiOperation("查询自动任务执行日志")
    @PostMapping("/findAutoTaskLog")
    public CustomPage<ScheduleJobLogPo> findAutoTaskLog(@RequestBody ScheduleJobLogVo scheduleJobLogVo) {
        return scheduleJobLogService.findAutoTaskLog(scheduleJobLogVo);
    }


}
