package com.billow.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import com.billow.system.dao.ApplyInfoDao;
import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.service.ApplyInfoService;
import com.billow.system.service.StartApplyProcess;
import com.billow.tools.enums.ApplyTypeEnum;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 申请信息服务
 *
 * @author liuyongtao
 * @create 2019-09-02 17:17
 */
@Service
public class ApplyInfoServiceImpl implements ApplyInfoService {

    @Autowired
    private Map<String, StartApplyProcess> startApplyProcessMap;
    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private WorkFlowQuery workFlowQuery;
    @Autowired
    private ApplyInfoDao applyInfoDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void submitApplyInfo(String operator, ApplyTypeEnum applyTypeEnum, Object object) {
        // 保存申请数据
        ApplyInfoPo applyInfo = new ApplyInfoPo();
        applyInfo.setApplyData(JSONObject.toJSONString(object));
        applyInfo.setApplyType(applyTypeEnum.getApplyType());
        applyInfo.setIsEnd(false);
        applyInfo = applyInfoDao.save(applyInfo);
        // 启动相应流程
        String key = applyTypeEnum.getApplyType() + StartApplyProcess.class.getSimpleName();
        StartApplyProcess startApplyProcess = startApplyProcessMap.get(key);
        Map<String, Object> variables = new HashMap<>();
        // 启动前操作
        if (startApplyProcess != null) {
            startApplyProcess.startProcessBefore(variables, object);
        }
        // 启动流程
        ProcessInstanceVo processInstanceVo = workFlowExecute.startProcessInstance(operator,
                applyTypeEnum.getProcessKey(),
                applyInfo.getId().toString(),
                variables);

        // 查询任务号
        List<Task> taskList = workFlowQuery.queryTasksByProcessId(processInstanceVo.getProcessInstanceId());
        if (taskList == null || taskList.size() == 0) {
            applyInfo.setIsEnd(true);
            String tasks = taskList.stream().map(m -> m.getId()).collect(Collectors.joining(","));
            applyInfo.setTaskId(tasks);
        }

        // 更新申请信息
        applyInfo.setProcessDefinitionId(processInstanceVo.getProcessDefinitionId());
        applyInfo.setProcessInstanceId(processInstanceVo.getProcessInstanceId());
        applyInfoDao.save(applyInfo);

        // 启动流程后操作
        if (startApplyProcess != null) {
            startApplyProcess.startProcessAfter(applyInfo);
        }
    }
}
