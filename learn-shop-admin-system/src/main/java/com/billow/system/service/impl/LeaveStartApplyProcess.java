package com.billow.system.service.impl;

import com.billow.system.pojo.ex.LeaveEx;
import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.service.StartApplyProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 请假申请流程启动回调
 *
 * @author liuyongtao
 * @create 2019-09-02 17:37
 */
@Slf4j
@Service
public class LeaveStartApplyProcess implements StartApplyProcess<LeaveEx> {

    @Override
    public void startProcessBefore(Map<String, Object> variables, LeaveEx leaveEo) {
        variables.put("startDate", leaveEo.getStartDate());
        variables.put("endDate", leaveEo.getEndDate());
        variables.put("reason", leaveEo.getReason());
    }

    @Override
    public void startProcessAfter(ApplyInfoPo applyInfoPo) {
        log.info("LeaveStartApplyProcess.startProcessAfter");
    }
}
