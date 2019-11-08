package com.billow.job.core.init;

import com.billow.job.core.enumType.AutoTaskJobStatusEnum;
import com.billow.job.core.manager.QuartzManager;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.job.service.ScheduleJobService;
import com.billow.job.util.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化，加载所有的自动任务
 *
 * @author liuyongtao
 * @date 2017年5月12日 下午6:44:08
 */
@Slf4j
@Component
public class InitJob implements InitializingBean {

    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private QuartzManager quartzManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("==========初始化自动任务开始==========");
        try {
            ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
            scheduleJobVo.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus());
            scheduleJobVo.setValidInd(true);
            List<ScheduleJobVo> list = scheduleJobService.findByJobStatus(scheduleJobVo);
            if (ToolsUtils.isNotEmpty(list)) {
                quartzManager.addJobList(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("初始化自动任务失败：" + e);
        }
        log.info("==========初始化自动任务结束==========");
    }
}
