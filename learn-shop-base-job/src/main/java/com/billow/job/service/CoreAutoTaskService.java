package com.billow.job.service;


import com.billow.job.pojo.vo.ScheduleJobVo;

/**
 * 任务管理
 *
 * @author liuyongtao
 * @date 2017年5月12日 下午5:29:33
 */
public interface CoreAutoTaskService {

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
    void updateJobStatus(ScheduleJobVo dto) throws Exception;

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
    void deleteAutoTask(Long jobId) throws Exception;

    /**
     * 保存/更新自动任务
     *
     * @param scheduleJobVo
     * @return 返回错误信息
     * @throws Exception
     * @author XiaoY
     * @date: 2017年5月22日 上午10:08:48
     */
    void saveAutoTask(ScheduleJobVo scheduleJobVo) throws Exception;

    /**
     * 校验自动任务添加、修改时参数的设置
     *
     * @param scheduleJobVo
     * @return
     */
    ScheduleJobVo checkAutoTask(ScheduleJobVo scheduleJobVo) throws Exception;

    /**
     * 立即执行自动任务
     *
     * @param scheduleJobVo
     */
    void immediateExecutionTask(ScheduleJobVo scheduleJobVo) throws Exception;

//    /**
//     * 插入自动任务中异常信息并且信息自动任务标识为异常
//     *
//     * @param jobExecutionContext
//     * @param exception           异常信息
//     * @throws Exception
//     */
//    void insertAutoTaskException(JobExecutionContext jobExecutionContext, Exception exception) throws Exception;
}
