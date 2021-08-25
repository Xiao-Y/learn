package com.billow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.ProcessInstanceVo;
//import com.billow.mybatis.utils.MybatisKet;
import com.billow.mybatis.utils.MybatisKet;
import com.billow.system.dao.ApplyInfoDao;
import com.billow.system.dao.MytasklistDao;
import com.billow.system.feign.AdminUserFeign;
import com.billow.system.pojo.ex.LeaveEx;
import com.billow.system.pojo.ex.UserEx;
import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.pojo.po.MytasklistPo;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 申请信息服务
 *
 * @author liuyongtao
 * @create 2019-09-02 17:17
 */
@Slf4j
@Service
public class ApplyInfoServiceImpl extends ServiceImpl<ApplyInfoDao, ApplyInfoPo> implements ApplyInfoService {

    @Autowired
    private Map<String, StartApplyProcess> startApplyProcessMap;
    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private WorkFlowQuery workFlowQuery;
    @Autowired
    private ApplyInfoDao applyInfoDao;
    @Autowired
    private MytasklistDao mytasklistDao;
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
//        applyInfo = applyInfoDao.save(applyInfo);
        this.save(applyInfo);

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
        this.updateById(applyInfo);

        // 启动流程后操作
        if (startApplyProcess != null) {
            startApplyProcess.startProcessAfter(applyInfo);
        }
    }

    @Override
    public IPage<MytasklistPo> queryMyTaskList(ApplyInfoVo applyInfoVo, Integer offset, Integer pageSize) {
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

        UserEx resData = baseResponse.getResData();

        IPage<MytasklistPo> page = new Page<>(offset, pageSize);
        LambdaQueryWrapper<MytasklistPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MytasklistPo::getAssignee, assignee);
        if (resData != null && ToolsUtils.isNotEmpty(resData.getGroupId())) {
            wrapper.or().eq(ToolsUtils.isNotEmpty(resData.getGroupId()), MytasklistPo::getGroupId, resData.getGroupId());
        }
        IPage<MytasklistPo> applyInfoPoIPage = mytasklistDao.selectPage(page, wrapper);
        return applyInfoPoIPage;
    }

    @Override
    public IPage<ApplyInfoPo> myStartProdeList(ApplyInfoVo applyInfoVo) {
        ApplyInfoPo applyInfoPo = ConvertUtils.convert(applyInfoVo, ApplyInfoPo.class);
        IPage<ApplyInfoPo> page = new Page<>(applyInfoVo.getPageNo(), applyInfoVo.getPageSize());
        QueryWrapper<ApplyInfoPo> condition = MybatisKet.getCondition(applyInfoPo);
        condition.orderByDesc("createTime");
        IPage<ApplyInfoPo> applyInfoPoIPage = this.page(page, condition);
        return applyInfoPoIPage;
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
        ApplyInfoPo applyInfoPo = applyInfoDao.selectById(id);
        if (!applyInfoPo.getIsEnd()) {
            throw new RuntimeException("流程未结束不能删除");
        }
        applyInfoDao.deleteById(id);
    }

    @Override
    public ApplyInfoVo findLeaveById(Long id) {
        ApplyInfoPo infoPo = applyInfoDao.selectById(id);
        return ConvertUtils.convert(infoPo, ApplyInfoVo.class);
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
            ApplyInfoPo applyInfo = applyInfoDao.selectById(leaveEx.getId());
            applyInfo.setApplyData(applyData);
            applyInfoDao.updateById(applyInfo);
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
