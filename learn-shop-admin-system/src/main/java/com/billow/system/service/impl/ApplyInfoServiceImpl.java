package com.billow.system.service.impl;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.CustomPage;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import com.billow.system.dao.ApplyInfoDao;
import com.billow.system.feign.AdminUserFeign;
import com.billow.system.pojo.ex.LeaveEx;
import com.billow.system.pojo.ex.UserEx;
import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.pojo.vo.ApplyInfoVo;
import com.billow.system.service.ApplyInfoService;
import com.billow.system.service.StartApplyProcess;
import com.billow.tools.enums.ApplyTypeEnum;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.enums.SubmitTypeEnum;
import com.billow.tools.resData.BaseResponse;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.task.Task;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 申请信息服务
 *
 * @author liuyongtao
 * @create 2019-09-02 17:17
 */
@Slf4j
@Service
public class ApplyInfoServiceImpl implements ApplyInfoService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private Map<String, StartApplyProcess> startApplyProcessMap;
    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private WorkFlowQuery workFlowQuery;
    @Autowired
    private ApplyInfoDao applyInfoDao;
    @Autowired
    private AdminUserFeign adminUserFeign;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void submitApplyInfo(String operator, ApplyTypeEnum applyTypeEnum, Object object) {
        // 保存申请数据
        ApplyInfoPo applyInfo = new ApplyInfoPo();
        String key = applyTypeEnum.getApplyType() + StartApplyProcess.class.getSimpleName();
        StartApplyProcess startApplyProcess = startApplyProcessMap.get(key);

        if (startApplyProcess != null) {
            // 构建 applyData 数据
            String applyData = startApplyProcess.genApplyData(object);
            applyInfo.setApplyData(applyData);
        }
        applyInfo.setApplyType(applyTypeEnum.getApplyType());
        applyInfo.setIsEnd(false);
        applyInfo.setApplyUserCode(operator);
        applyInfo.setValidInd(true);
        // 保存申请信息
        applyInfo = applyInfoDao.save(applyInfo);

        // 启动相应流程
        Map<String, Object> variables = null;
        // 启动前操作
        if (startApplyProcess != null) {
            variables = startApplyProcess.startProcessBefore(object);
        }
        // 启动流程
        ProcessInstanceVo processInstanceVo = workFlowExecute.startProcessInstance(operator,
                applyTypeEnum.getProcessKey(),
                applyInfo.getId().toString(),
                variables);

        // 更新申请信息
        applyInfo.setProcDefId(processInstanceVo.getProcessDefinitionId());
        applyInfo.setProcInstId(processInstanceVo.getProcessInstanceId());
        applyInfoDao.save(applyInfo);

        // 启动流程后操作
        if (startApplyProcess != null) {
            startApplyProcess.startProcessAfter(applyInfo);
        }
    }

//    @Override
//    public void submitReWorkApplyInfo(ApplyTypeEnum applyTypeEnum, Long id, String taskId, Object object) {
//        String key = applyTypeEnum.getApplyType() + StartApplyProcess.class.getSimpleName();
//        StartApplyProcess startApplyProcess = startApplyProcessMap.get(key);
//
//        Map<String, Object> variables = null;
//        if (startApplyProcess != null) {
//            // 构建 applyData 数据
//            String applyData = startApplyProcess.genApplyData(object);
//            ApplyInfoPo applyInfo = applyInfoDao.findOne(id);
//            applyInfo.setApplyData(applyData);
//            applyInfoDao.save(applyInfo);
//            // 重新提交时，设置工作流运行参数
//            variables = startApplyProcess.submitReWorkBefore(object);
//        }
//        // 执行工作流
//        workFlowExecute.commitProcess(taskId, variables);
//    }

    @Override
    public CustomPage queryMyTaskList(ApplyInfoVo applyInfoVo, Integer offset, Integer pageSize) {
        StringBuilder sql = new StringBuilder("SELECT ");
        // 任务归属人
        sql.append("r.assignee AS assignee, ");
        // 任务归属分组
        sql.append("r.groupId AS groupId, ");
        sql.append("r.taskId AS taskId, ");
        sql.append("r.taskName AS taskName, ");
        // 是否被认领，0-已认领，1-未认领
        sql.append("r.claimStatus AS claimStatus, ");
        // 是否活动，1-活动，2-挂起
        sql.append("r.suspensionStatus AS suspensionStatus, ");
        sql.append("r.id AS id, ");
//        sql.append("r.apply_data as applyData, ");
        sql.append("r.procDefId AS procDefId, ");
        sql.append("r.procInstId AS procInstId, ");
        sql.append("r.isEnd AS isEnd, ");
        sql.append("if(r.isEnd,'1','0') AS isEndStatus, ");
        sql.append("r.applyType AS applyType, "); // 申请类型
        sql.append("r.applyUserCode AS applyUserCode, ");// 申请人
        sql.append("r.validInd AS validInd, ");
        sql.append("r.createTime AS createTime, ");
        sql.append("r.creatorCode AS creatorCode, ");
        sql.append("r.updateTime AS updateTime, ");
        sql.append("r.updaterCode AS updaterCode ");
        sql.append("FROM v_mytasklist r WHERE 1 =1 ");

        String assignee = applyInfoVo.getAssignee();
        if (ToolsUtils.isEmpty(assignee)) {
            log.error("assignee 不能为空");
            new RuntimeException("assignee 不能为空");
        }

        BaseResponse<UserEx> baseResponse = adminUserFeign.findUserInfoByUserCode(assignee);
        String resCode = baseResponse.getResCode();
        if (!resCode.equals(ResCodeEnum.OK)) {
            log.error("远程调用 AdminUser 系统异常");
            new RuntimeException("远程调用 AdminUser 系统异常");
        }

        StringBuilder sqlCondition = new StringBuilder();

        UserEx resData = baseResponse.getResData();
        if (resData != null && ToolsUtils.isNotEmpty(resData.getGroupId())) {
            sqlCondition.append("and r.assignee = '" + assignee + "' or r.groupId = '" + resData.getGroupId() + "'");
        } else {
            sqlCondition.append("and r.assignee = '" + assignee + "'");
        }

        sql.append(sqlCondition);
        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        nativeQuery.setFirstResult(offset);
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> resultList = nativeQuery.getResultList();

        StringBuilder sqlCount = new StringBuilder("SELECT count(1) from v_mytasklist r where 1=1 ");
        sqlCount.append(sqlCondition);
        nativeQuery = entityManager.createNativeQuery(sqlCount.toString());
        BigInteger o = (BigInteger) nativeQuery.getSingleResult();
        CustomPage<Map<String, Object>> page = new CustomPage<>(pageSize, o.longValue());
        page.setTableData(resultList);
        return page;
    }

    @Override
    public Page<ApplyInfoVo> myStartProdeList(ApplyInfoVo applyInfoVo) {
        ApplyInfoPo applyInfoPo = ConvertUtils.convert(applyInfoVo, ApplyInfoPo.class);
        Pageable pageable = PageRequest.of(applyInfoVo.getPageNo(), applyInfoVo.getPageSize(), Sort.Direction.DESC, "createTime");
        Page<ApplyInfoVo> page = applyInfoDao.findAll(Example.of(applyInfoPo), pageable).map(this::applyInfoPoToVo);
        return page;
    }

    private ApplyInfoVo applyInfoPoToVo(ApplyInfoPo applyInfoPo) {
        ApplyInfoVo vo = new ApplyInfoVo();
        BeanUtils.copyProperties(applyInfoPo, vo);
        if (vo.getIsEnd()) {
            return vo;
        }
        String procInstId = vo.getProcInstId();
        int status = workFlowQuery.querySuspensionStatus(procInstId);
        vo.setSuspensionStatus(status);
        return vo;
    }

    @Override
    public void deleteApplyInfoById(Long id) {
        Optional<ApplyInfoPo> applyInfoPo = applyInfoDao.findById(id);
        if (!applyInfoPo.get().getIsEnd()) {
            throw new RuntimeException("流程未结束不能删除");
        }
        applyInfoDao.deleteById(id);
    }

    @Override
    public ApplyInfoVo findLeaveById(Long id) {
        Optional<ApplyInfoPo> infoPo = applyInfoDao.findById(id);
        return ConvertUtils.convert(infoPo.orElse(new ApplyInfoPo()), ApplyInfoVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void commitLeaveProcess(String operator, ApplyTypeEnum applyTypeEnum, LeaveEx leaveEx, String procInstId, String taskId) {
        String submitType = leaveEx.getSubmitType();
        SubmitTypeEnum submitTypeEnum = SubmitTypeEnum.getSubmitTypeEnum(submitType);
        leaveEx.setTransFlag(submitTypeEnum.getTransFlag());

        String key = applyTypeEnum.getApplyType() + StartApplyProcess.class.getSimpleName();
        StartApplyProcess startApplyProcess = startApplyProcessMap.get(key);
        if (startApplyProcess != null) {
            // 构建 applyData 数据
            String applyData = startApplyProcess.genApplyData(leaveEx);
            Optional<ApplyInfoPo> optional = applyInfoDao.findById(leaveEx.getId());
            ApplyInfoPo applyInfo = optional.get();
            applyInfo.setApplyData(applyData);
            applyInfoDao.save(applyInfo);
        }

        // 保存批注信息
        if (ToolsUtils.isNotEmpty(leaveEx.getComment())) {
            workFlowExecute.addComment(operator, procInstId, taskId, leaveEx.getComment());
        }
        // 提交任务
        Map<String, Object> variables = new HashMap<>();
        variables.put("transFlag", leaveEx.getTransFlag());
        workFlowExecute.commitProcess(taskId, variables);
        // 设置任务所属人，当 taskCode 为空时，会将新任务都分配给 userId ，否则只会分配指定的 taskCode
        workFlowExecute.setAssignee(procInstId, leaveEx.getAssignee(), leaveEx.getTaskCode());
    }
}
