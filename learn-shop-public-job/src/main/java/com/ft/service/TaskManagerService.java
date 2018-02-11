package com.ft.service;


import com.ft.model.custom.JsonResult;
import com.ft.model.expand.ScheduleJobDto;

/**
 * 任务管理
 *
 * @author liuyongtao
 * @date 2017年5月12日 下午5:29:33
 */
public interface TaskManagerService {

    /**
     * 启用、禁用自动任务
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param dto
     * @throws Exception
     * @date 2017年5月12日 下午5:36:01
     */
    void updateJobStatus(ScheduleJobDto dto) throws Exception;

    /**
     * 删除自动任务
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param jobId
     * @throws Exception
     * @date 2017年5月12日 下午7:17:41
     */
    void deleteAutoTask(int jobId) throws Exception;

    /**
     * 保存/更新自动任务
     *
     * @param scheduleJobDto
     * @return 返回错误信息
     * @throws Exception
     * @author XiaoY
     * @date: 2017年5月22日 上午10:08:48
     */
    void saveAutoTask(ScheduleJobDto scheduleJobDto) throws Exception;

    /**
     * 校验自动任务添加、修改时参数的设置
     *
     * @param scheduleJobDto
     * @return
     */
    JsonResult checkAutoTask(ScheduleJobDto scheduleJobDto) throws Exception;

    /**
     * 立即执行自动任务
     *
     * @param scheduleJobDto
     */
    void immediateExecutionTask(ScheduleJobDto scheduleJobDto) throws Exception;
}
