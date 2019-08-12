package com.billow.job.api;

import com.billow.common.base.BaseApi;
import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.service.ScheduleJobService;
import com.billow.job.service.TaskManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    private TaskManagerService taskManagerService;

    @ApiOperation("查询自动任务列表")
    @PostMapping("/findAutoTask")
    public Page<ScheduleJobPo> findAutoTask(@RequestBody ScheduleJobVo scheduleJobVo) {
        Page<ScheduleJobPo> jods = scheduleJobService.selectAll(scheduleJobVo);
        return jods;
    }

    @ApiOperation("自动任务修改页面,jobId jobId为null表示是添加，不为null表示修改")
    @PostMapping("/editAutoTask")
    public ScheduleJobVo editAutoTask(ScheduleJobVo scheduleJobVo) {
        Long jobId = scheduleJobVo.getId();
        if (jobId != null) {// 表示编辑
            ScheduleJobVo dto = new ScheduleJobVo();
            dto.setId(jobId);
            return scheduleJobService.selectByPrimaryKey(dto);
        }
        return scheduleJobVo;
    }

    @ApiOperation("启用、禁用自动任务")
    @ApiImplicitParams({@ApiImplicitParam(dataType = "Integer", name = "jobId", value = "自动任务id", required = true),
            @ApiImplicitParam(dataType = "String", name = "jobStatus", value = "任务状态，0-禁用，1-启用", required = true)})
    @PutMapping("/updateJobStatus/{jobId}")
    public void updateJobStatus(@PathVariable("jobId") Long jobId, String jobStatus) throws Exception {
        ScheduleJobVo dto = new ScheduleJobVo();
        dto.setId(jobId);
        dto.setJobStatus(jobStatus);
        dto.setUpdateTime(new Date());
        taskManagerService.updateJobStatus(dto);
    }

    @ApiOperation("根据任务id,删除自动任务")
    @ApiParam(name = "jobId", value = "自动任务id")
    @DeleteMapping("/deleteAutoTask/{jobId}")
    public void deleteAutoTask(@PathVariable("jobId") Long jobId) throws Exception {
        taskManagerService.deleteAutoTask(jobId);
    }

    @ApiOperation("保存自动任务")
    @PostMapping("/saveAutoTask")
    public ScheduleJobVo saveAutoTask(ScheduleJobVo scheduleJobVo) throws Exception {
        taskManagerService.saveAutoTask(scheduleJobVo);
        return scheduleJobVo;
    }

    @ApiOperation("立即执行自动任务")
    @PostMapping("/immediateExecutionTask")
    public ScheduleJobVo immediateExecutionTask(ScheduleJobVo scheduleJobVo) throws Exception {
        taskManagerService.immediateExecutionTask(scheduleJobVo);
        return scheduleJobVo;
    }

    @ApiOperation("校验自动任务添加、修改时参数的设置")
    @PostMapping("/checkAutoTask")
    public ScheduleJobVo checkAutoTask(ScheduleJobVo scheduleJobVo) throws Exception {
        taskManagerService.checkAutoTask(scheduleJobVo);
        return scheduleJobVo;
    }
}
