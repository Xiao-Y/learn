package com.billow.system.service;

import com.billow.system.pojo.po.ApplyInfoPo;

import java.util.Map;

/**
 * 启动申请流程实例的回调<br/>
 * 实现类的名称是 ApplyTypeEnum 中的 applyType（首字母大写） + StartApplyProcess， 如：LeaveStartApplyProcess。<br/>
 * 如果不遵循请设置spring 容器中的名称为 applyType + StartApplyProcess
 *
 * @author LiuYongTao
 * @date 2019/9/2 17:36
 */
public interface StartApplyProcess<T> {


    /**
     * 流程启动之前，主要用于设置启动参数
     *
     * @param variables 启动工作流的参数
     * @param t         调用 submitApplyInfo 方法传入的参数类型一致
     * @return void
     * @author LiuYongTao
     * @date 2019/9/2 17:41
     */
    default void startProcessBefore(Map<String, Object> variables, T t) {
    }

    /**
     * 流程启动之后，主要用于返回启动后参数
     *
     * @param applyInfo
     * @return void
     * @author LiuYongTao
     * @date 2019/9/2 17:41
     */
    default void startProcessAfter(ApplyInfoPo applyInfo) {
    }
}
