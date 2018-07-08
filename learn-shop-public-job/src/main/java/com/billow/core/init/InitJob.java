package com.billow.core.init;

import com.billow.core.enumType.AutoTaskJobStatusEnum;
import com.billow.core.manager.QuartzManager;
import com.billow.pojo.vo.ScheduleJobVo;
import com.billow.service.ScheduleJobService;
import com.billow.tools.utlis.ToolsUtils;
import org.apache.log4j.Logger;

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
@Component
public class InitJob implements InitializingBean {
    private static final Logger logger = Logger.getLogger(InitJob.class);

    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private QuartzManager quartzManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("==============================初始化自动任务开始===========================");
        try {
            ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
            scheduleJobVo.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus());
            List<ScheduleJobVo> list = scheduleJobService.findByJobStatus(scheduleJobVo);
            if (ToolsUtils.isNotEmpty(list)) {
                quartzManager.addJobList(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化自动任务失败：" + e);
        }
        logger.info("==============================初始化自动任务结束===========================");
    }
}
