package com.billow.system.service;

import com.billow.base.workflow.vo.CustomPage;
import com.billow.system.pojo.ex.LeaveEx;
import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.pojo.vo.ApplyInfoVo;
import com.billow.tools.enums.ApplyTypeEnum;
import org.springframework.data.domain.Page;

public interface ApplyInfoService<T> {

    /**
     * 提交申请信息，如果存在 applyType + StartApplyProcess 的实现类，会执行其中的方法
     *
     * @param operator
     * @param t
     * @param applyType
     * @return void
     * @author LiuYongTao
     * @date 2019/9/2 17:21
     */
    void submitApplyInfo(String operator, ApplyTypeEnum applyType, T t);

    /**
     * 加载我的任务任务列表（已签收和未签收的）
     *
     * @param applyInfoVo
     * @param offset
     * @param pageSize
     * @return com.billow.base.workflow.vo.Page<com.billow.base.workflow.vo.TaskVo>
     * @author billow
     * @date 2019/9/3 20:03
     */
    CustomPage queryMyTaskList(ApplyInfoVo applyInfoVo, Integer offset, Integer pageSize);

    /**
     * 我发起的流程（所有的）
     *
     * @param applyInfoVo
     * @return com.billow.base.workflow.vo.Page
     * @author LiuYongTao
     * @date 2019/9/6 9:02
     */
    Page<ApplyInfoPo> myStartProdeList(ApplyInfoVo applyInfoVo);

    /**
     * 删除已经结束的申请
     *
     * @param id
     * @return void
     * @author LiuYongTao
     * @date 2019/9/6 11:39
     */
    void deleteApplyInfoById(Long id);

    /**
     * 根据ID查询申请信息
     *
     * @param id
     * @return com.billow.system.pojo.vo.ApplyInfoVo
     * @author billow
     * @date 2019/9/7 12:07
     */
    ApplyInfoVo findLeaveById(Long id);

    /**
     * 提交请假任务
     *
     * @param currentUserCode
     * @param taskId
     * @param leaveEx
     * @return void
     * @author billow
     * @date 2019/9/8 10:30
     */
    void commitLeaveProcess(String currentUserCode, String procInstId, String taskId, LeaveEx leaveEx);
}
