package com.billow.system.service;

import com.billow.base.workflow.vo.Page;
import com.billow.base.workflow.vo.TaskVo;
import com.billow.system.pojo.vo.ApplyInfoVo;
import com.billow.tools.enums.ApplyTypeEnum;

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
    Page queryMyTaskList(ApplyInfoVo applyInfoVo, Integer offset, Integer pageSize);
}
