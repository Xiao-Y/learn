package com.ft.autoTask.sysEvent;

import com.ft.enums.SysEventEunm;
import com.ft.sysEvent.model.expand.SysEventProcessDto;
import com.ft.sysEvent.service.SysEventProcessService;
import com.ft.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 待处理的事件
 *
 * @author liuyongtao
 * @create 2018-03-01 9:25
 */
public class SysEventProcessAutoTask {

    @Autowired
    private SysEventProcessService sysEventProcessService;

    public void sysEventProcess() {
        List<SysEventProcessDto> sysEventProcessDtos = sysEventProcessService.findByStatus(SysEventEunm.status_new.getStatusCode());
        if (ToolsUtils.isNotEmpty(sysEventProcessDtos)) {
            for (SysEventProcessDto dto : sysEventProcessDtos) {
                boolean flag = false;
                try {
                    //1.修改事件状态为已发布
                    dto.setStatus(SysEventEunm.status_processed.getStatusCode());
                    sysEventProcessService.update(dto);
                    //2.发送MQ
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = true;
                } finally {
                    if (flag) {//发生了异常
                        //修改事件状态为异常
                        dto.setStatus(SysEventEunm.status_exception.getStatusCode());
                        sysEventProcessService.update(dto);
                    }
                }
            }
        }
    }
}
