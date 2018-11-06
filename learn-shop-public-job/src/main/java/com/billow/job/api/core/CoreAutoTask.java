package com.billow.job.api.core;

import com.billow.common.base.BaseApi;
import com.billow.common.constant.MessageTipsCst;
import com.billow.common.constant.PagePathCst;
import com.billow.common.resData.BaseResponse;
import com.billow.job.pojo.ex.JsonResult;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 核心自动任务控制类
 *
 * @author liuyongtao
 * @create 2018-07-02 9:06
 */
@Api(value = "CoreAutoTask", description = "核心自动任务控制类")
@RestController
@RequestMapping("/coreAutoTask")
public class CoreAutoTask extends BaseApi {
    private static final Logger logger = LoggerFactory.getLogger(CoreAutoTask.class);

    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private TaskManagerService taskManagerService;

    @ApiOperation(value = "显示自动任务列表", notes = "用于显示自动任务列表")
    @PostMapping("/findAutoTask")
    public BaseResponse<Page<ScheduleJobPo>> findAutoTask(@RequestBody ScheduleJobVo scheduleJobVo) {
        System.out.println(scheduleJobVo);
        BaseResponse<Page<ScheduleJobPo>> baseResponse = new BaseResponse<>();
//        ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
        Page<ScheduleJobPo> jods = scheduleJobService.selectAll(scheduleJobVo);


//        PageHelper.startPage();
//        List<ScheduleJobVo> jods = scheduleJobService.selectAll(scheduleJobVo);
//        PageInfo<ScheduleJobVo> page = new PageInfo<>(jods);
//        av.addObject("page", page);
//        av.setViewName(PagePathCst.BASEPATH_AUTOTASK + "autoTaskManage");
        baseResponse.setResData(jods);
        return baseResponse;
    }

    @ApiOperation(value = "自动任务修改", notes = "自动任务修改页面,jobId jobId为null表示是添加，不为null表示修改")
    @PostMapping("/editAutoTask")
    public ModelAndView editAutoTask(ScheduleJobVo scheduleJobVo) {
        ModelAndView av = new ModelAndView();
        Long jobId = scheduleJobVo.getId();
        if (jobId != null) {// 表示编辑
            ScheduleJobVo dto = new ScheduleJobVo();
            dto.setId(jobId);
            dto = scheduleJobService.selectByPrimaryKey(dto);
            av.addObject("task", dto);
        }
        av.setViewName(PagePathCst.BASEPATH_AUTOTASK + "autoTaskEdit");
        av.addObject("pageNo", scheduleJobVo.getPageNo());
        return av;
    }

    @ApiOperation(value = "启用、禁用自动任务", notes = "启用、禁用自动任务.任务状态，0-禁用，1-启用")
    @ApiImplicitParams({@ApiImplicitParam(dataType = "Integer", name = "jobId", value = "自动任务id", required = true),
            @ApiImplicitParam(dataType = "String", name = "jobStatus", value = "任务状态，0-禁用，1-启用", required = true)})
    @PutMapping("/updateJobStatus/{jobId}")
    public JsonResult updateJobStatus(@PathVariable("jobId") Long jobId, String jobStatus, Integer pageNum) {
        JsonResult json = new JsonResult();
        ScheduleJobVo dto = new ScheduleJobVo();
        dto.setId(jobId);
        dto.setJobStatus(jobStatus);
        dto.setUpdateTime(new Date());
        try {
            taskManagerService.updateJobStatus(dto);
            json.setType(MessageTipsCst.TYPE_SUCCES);
            json.setMessage(MessageTipsCst.UPDATE_SUCCESS);
            json.setRoot("/sysAutoTask/findAutoTask?pageNum" + pageNum);
        } catch (Exception e) {
            e.printStackTrace();
            json.setType(MessageTipsCst.TYPE_ERROR);
            json.setMessage(MessageTipsCst.UPDATE_FAILURE);
            logger.error(e.getMessage());
        }
        return json;
    }

    @ApiOperation(value = "删除自动任务", notes = "根据任务id,删除自动任务")
    @ApiParam(name = "jobId", value = "自动任务id")
    @DeleteMapping("/deleteAutoTask/{jobId}")
    public JsonResult deleteAutoTask(@PathVariable("jobId") Long jobId) {
        JsonResult json = new JsonResult();
        try {
            taskManagerService.deleteAutoTask(jobId);
            json.setType(MessageTipsCst.TYPE_SUCCES);
            json.setMessage(MessageTipsCst.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            json.setType(MessageTipsCst.TYPE_ERROR);
            json.setMessage(MessageTipsCst.UPDATE_FAILURE);
            logger.error(e.getMessage());
        }
        return json;
    }

    @ApiOperation(value = "保存自动任务", notes = "保存自动任务")
    @PostMapping("/saveAutoTask")
    public JsonResult saveAutoTask(ScheduleJobVo scheduleJobVo) {
        JsonResult json = new JsonResult();
        try {
            taskManagerService.saveAutoTask(scheduleJobVo);
            json.setType(MessageTipsCst.TYPE_SUCCES);
            json.setMessage(MessageTipsCst.UPDATE_SUCCESS);
            json.setRoot("/sysAutoTask/findAutoTask");
        } catch (Exception e) {
            e.printStackTrace();
            json.setType(MessageTipsCst.TYPE_ERROR);
            json.setMessage(MessageTipsCst.UPDATE_FAILURE);
            logger.error(e.getMessage());
        }
        return json;
    }

    @ApiOperation(value = "立即执行自动任务", notes = "立即执行自动任务")
    @PostMapping("/immediateExecutionTask")
    public JsonResult immediateExecutionTask(ScheduleJobVo scheduleJobVo) {
        JsonResult json = new JsonResult();
        try {
            taskManagerService.immediateExecutionTask(scheduleJobVo);
            json.setType(MessageTipsCst.TYPE_SUCCES);
            json.setMessage(MessageTipsCst.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            json.setType(MessageTipsCst.TYPE_ERROR);
            json.setMessage(MessageTipsCst.SERVICE_ERRER);
            logger.error(e.getMessage());
        }
        return json;
    }

    @ApiOperation(value = "校验自动任务添加、修改时参数的设置", notes = "校验自动任务添加、修改时参数的设置")
    @PostMapping("/checkAutoTask")
    public JsonResult checkAutoTask(ScheduleJobVo scheduleJobVo) {
        JsonResult json = new JsonResult();
        try {
            json = taskManagerService.checkAutoTask(scheduleJobVo);
        } catch (Exception e) {
            e.printStackTrace();
            json.setType(MessageTipsCst.TYPE_ERROR);
            json.setMessage(MessageTipsCst.SERVICE_ERRER);
            logger.error(e.getMessage());
        }
        return json;
    }
}
