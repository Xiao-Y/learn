package com.billow.system.service.impl;

import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.pojo.vo.LeaveVo;
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
public class LeaveStartApplyProcess implements StartApplyProcess<LeaveVo> {

    @Override
    public void startProcessBefore(Map<String, Object> variables, LeaveVo leaveVo) {
        variables.put("startDate", leaveVo.getStartDate());
        variables.put("endDate", leaveVo.getEndDate());
        variables.put("reason", leaveVo.getReason());
    }

    @Override
    public void startProcessAfter(ApplyInfoPo applyInfoPo) {
        log.info("LeaveStartApplyProcess.startProcessAfter");
    }
}
