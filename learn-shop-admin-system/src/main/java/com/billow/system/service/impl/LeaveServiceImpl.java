package com.billow.system.service.impl;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.system.dao.LeaveDao;
import com.billow.system.pojo.po.LeavePo;
import com.billow.system.pojo.vo.LeaveVo;
import com.billow.system.service.LeaveService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyongtao
 * @create 2019-09-01 14:21
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private LeaveDao leaveDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void submitLeave(String currentUserCode, LeaveVo leaveVo) {
        LeavePo leavePo = ConvertUtils.convert(leaveVo, LeavePo.class);
        leavePo = leaveDao.save(leavePo);
        Map<String, Object> variables = new HashMap<>();
        variables.put("startDate", leavePo.getStartDate());
        variables.put("endDate", leavePo.getEndDate());
        variables.put("reason", leavePo.getReason());
        workFlowExecute.startProcessInstance(currentUserCode, "process_pool", leavePo.getId().toString(), variables);
    }
}
