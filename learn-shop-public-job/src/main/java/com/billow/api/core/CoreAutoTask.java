package com.billow.api.core;

import com.billow.common.constant.MessageTipsCst;
import com.billow.common.constant.PagePathCst;
import com.billow.model.custom.JsonResult;
import com.billow.model.expand.ScheduleJobDto;
import com.billow.service.ScheduleJobService;
import com.billow.service.TaskManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
@RequestMapping("/coreAutoTask")
public class CoreAutoTask {
    private static final Logger logger = LoggerFactory.getLogger(CoreAutoTask.class);

    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private TaskManagerService taskManagerService;

    /**
     * 显示自动任务列表
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJobDto
     * @return
     * @date 2017年5月11日 下午2:59:15
     */
    @RequestMapping("/findAutoTask")
    public ModelAndView findAutoTask(ScheduleJobDto scheduleJobDto) {
        ModelAndView av = new ModelAndView();
//        PageHelper.startPage();
//        List<ScheduleJobDto> jods = scheduleJobService.selectAll(scheduleJobDto);
//        PageInfo<ScheduleJobDto> page = new PageInfo<>(jods);
//        av.addObject("page", page);
//        av.setViewName(PagePathCst.BASEPATH_AUTOTASK + "autoTaskManage");
        return av;
    }

    /**
     * 自动任务修改页面
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJobDto jobId jobId为null表示是添加，不为null表示修改
     * @return
     * @date 2017年5月11日 下午2:59:31
     */
    @RequestMapping("/editAutoTask")
    public ModelAndView editAutoTask(ScheduleJobDto scheduleJobDto) {
        ModelAndView av = new ModelAndView();
        Integer jobId = scheduleJobDto.getJobId();
        if (jobId != null) {// 表示编辑
            ScheduleJobDto dto = new ScheduleJobDto();
            dto.setJobId(jobId);
            dto = scheduleJobService.selectByPrimaryKey(dto);
            av.addObject("task", dto);
        }
        av.setViewName(PagePathCst.BASEPATH_AUTOTASK + "autoTaskEdit");
        av.addObject("pageNo", scheduleJobDto.getPageNo());
        return av;
    }

    /**
     * 启用、禁用自动任务
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param jobId     自动任务id
     * @param jobStatus 任务状态，0-禁用，1-启用
     * @param pageNum   当前页
     * @return
     * @date 2017年5月11日 下午2:58:16
     */
    @RequestMapping("/updateJobStatus/{jobId}")
    public JsonResult updateJobStatus(@PathVariable("jobId") Integer jobId, String jobStatus, Integer pageNum) {
        JsonResult json = new JsonResult();
        ScheduleJobDto dto = new ScheduleJobDto();
        dto.setJobId(jobId);
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

    /**
     * 删除自动任务
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param jobId
     * @return
     * @date 2017年5月12日 上午8:42:28
     */
    @RequestMapping("/deleteAutoTask/{jobId}")
    public JsonResult deleteAutoTask(@PathVariable("jobId") int jobId) {
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

    /**
     * 保存自动任务
     *
     * @param scheduleJobDto
     * @return
     * @author XiaoY
     * @date: 2017年5月22日 上午10:07:54
     */
    @RequestMapping("/saveAutoTask")
    public JsonResult saveAutoTask(ScheduleJobDto scheduleJobDto) {
        JsonResult json = new JsonResult();
        try {
            taskManagerService.saveAutoTask(scheduleJobDto);
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

    /**
     * 立即执行自动任务
     *
     * @param scheduleJobDto
     * @return
     */
    @RequestMapping("/immediateExecutionTask")
    public JsonResult immediateExecutionTask(ScheduleJobDto scheduleJobDto) {
        JsonResult json = new JsonResult();
        try {
            taskManagerService.immediateExecutionTask(scheduleJobDto);
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

    /**
     * 校验自动任务添加、修改时参数的设置
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param scheduleJobDto
     * @return
     * @date 2017年5月14日 下午12:11:55
     */
    @RequestMapping("/checkAutoTask")
    public JsonResult checkAutoTask(ScheduleJobDto scheduleJobDto) {
        JsonResult json = new JsonResult();
        try {
            json = taskManagerService.checkAutoTask(scheduleJobDto);
        } catch (Exception e) {
            e.printStackTrace();
            json.setType(MessageTipsCst.TYPE_ERROR);
            json.setMessage(MessageTipsCst.SERVICE_ERRER);
            logger.error(e.getMessage());
        }
        return json;
    }
}
