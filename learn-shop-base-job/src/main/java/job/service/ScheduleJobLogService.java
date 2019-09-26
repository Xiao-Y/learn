package job.service;


import job.pojo.po.ScheduleJobLogPo;
import job.pojo.vo.ScheduleJobLogVo;
import org.springframework.data.domain.Page;

/**
 * 自动任务信息日志接口<br>
 *
 * @author billow<br>
 * @version 2.0
 * @Mail lyongtao123@126.com<br>
 * @date 2017-12-08 15:46:02
 */
public interface ScheduleJobLogService {

    /**
     * 插入执行日志
     *
     * @param logDto
     * @return void
     * @author LiuYongTao
     * @date 2019/9/26 16:27
     */
    void insert(ScheduleJobLogVo logDto);


    /**
     * 查询执行日志
     *
     * @param scheduleJobLogVo
     * @return org.springframework.data.domain.Page<com.billow.job.pojo.po.ScheduleJobLogPo>
     * @author LiuYongTao
     * @date 2019/8/16 16:17
     */
    Page<ScheduleJobLogPo> findAutoTaskLog(ScheduleJobLogVo scheduleJobLogVo);
}